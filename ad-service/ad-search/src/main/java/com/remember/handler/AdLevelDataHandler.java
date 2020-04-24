package com.remember.handler;

import com.alibaba.fastjson.JSON;
import com.remember.dump.table.*;
import com.remember.index.DataTable;
import com.remember.index.IndexAware;
import com.remember.index.adplan.AdPlanIndex;
import com.remember.index.adplan.AdPlanObject;
import com.remember.index.adunit.AdUnitIndex;
import com.remember.index.adunit.AdUnitObject;
import com.remember.index.creative.CreativeIndex;
import com.remember.index.creative.CreativeObject;
import com.remember.index.creativeunit.CreativeUnitIndex;
import com.remember.index.creativeunit.CreativeUnitObject;
import com.remember.index.district.UnitDistrictIndex;
import com.remember.index.interest.UnitItIndex;
import com.remember.index.interest.UnitItObject;
import com.remember.index.keyword.UnitKeywordIndex;
import com.remember.mysql.constant.OpType;
import com.remember.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
  * @author remember
  * @date 2020/4/24 12:30
 * 索引处理
 * 索引之间有层级划分（依赖关系划分） 第一层是用户，第二层是推广计划及创意（这俩与其他层级都没关联）
 * 索引层级划分的标准是该索引与其他索引的依赖关系，越独立（与其他索引依赖越少，如创意不依赖任何，而Unit
 * 却依赖于keyword/it等限制）层级越高（1是最高）。往往在底层级需要对高层级的索引进行校验。
 * 加载全量索引其实是增量索引“添加”的一种特殊实现
  */
@Slf4j
public class AdLevelDataHandler {

    /*
    推广计划索引
    AdPlanTable是从文件中获取到的JSON格式的数据，现在需要将其转换成索引。
     */
    public static void handleLevel2(AdPlanTable planTable,OpType type){
        AdPlanObject planObject = new AdPlanObject(planTable.getId(),planTable.getUserId(),
                planTable.getPlanStatus(),planTable.getStartDate(),planTable.getEndDate());
        handleBinlogEvent(DataTable.of(AdPlanIndex.class),planObject.getPlanId(),planObject,type);
    }
    public static void handleLevel2(AdCreativeTable creativeTable, OpType type){
        CreativeObject creativeObject = new CreativeObject(creativeTable.getAdId(),creativeTable.getName(),
                creativeTable.getType(),creativeTable.getMaterialType(),creativeTable.getHeight(),
                creativeTable.getWidth(),creativeTable.getAuditStatus(),creativeTable.getAdUrl());
        handleBinlogEvent(DataTable.of(CreativeIndex.class),creativeObject.getAdId(),creativeObject,type);
    }
    public static void handleLevel3(AdUnitTable unitTable,OpType type){
        AdPlanObject adPlanObject = DataTable.of(AdPlanIndex.class).get(unitTable.getPlanId());
        if(null == adPlanObject){
            log.error("handleLevel3 found AdPlanObject Error : {}",unitTable.getPlanId());
            return;
        }
        AdUnitObject unitObject = new AdUnitObject(unitTable.getUnitId(),unitTable.getUnitStatus(),
                unitTable.getPositionType(),unitTable.getPlanId(),adPlanObject);
        handleBinlogEvent(DataTable.of(AdUnitIndex.class),unitObject.getUnitId(),unitObject,type);
    }
    public static void handleLevel3(AdCreativeUnitTable creativeUnitTable, OpType type){
        if(type == OpType.UPDATE){
            log.error("Creative index service do not support update");
            return;
        }
        CreativeObject creativeObject = DataTable.of(CreativeIndex.class).get(creativeUnitTable.getAdId());
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(creativeUnitTable.getUnitId());
        if(null == creativeObject || null == unitObject){
            log.error("AdCreativeUnit index error");
        }
        CreativeUnitObject creativeUnitObject = new CreativeUnitObject(creativeUnitTable.getAdId(),
                creativeUnitTable.getUnitId());
        handleBinlogEvent(DataTable.of(CreativeUnitIndex.class),
                CommonUtils.stringConcat(creativeUnitObject.getAdId().toString(),
                        creativeUnitObject.getUnitId().toString()),creativeUnitObject,type);
    }
    public static void handleLevel4(AdUnitDistrictTable unitDistrictTable,OpType type){
        if(type == OpType.UPDATE){
            log.error("district index do not support update");
            return;
        }
        AdUnitObject adUnitObject = DataTable.of(AdUnitIndex.class).get(unitDistrictTable.getUnitId());
        if(null == adUnitObject){
            log.error("AdUnitDistrictTable index error : {}", unitDistrictTable.getUnitId());
        }
        String key = CommonUtils.stringConcat(unitDistrictTable.getProvince(),unitDistrictTable.getCity());
        Set<Long> value = new HashSet<>(
                Collections.singleton(unitDistrictTable.getUnitId())//这个方法代表返回的Set是不可变的。
        );
        handleBinlogEvent(DataTable.of(UnitDistrictIndex.class),key,value,type);
    }
    public static void handleLevel4(AdUnitItTable unitItTable,OpType type){
        if(type == OpType.UPDATE){
            log.error("it index do not support update");
            return;
        }
        AdUnitObject adUnitObject = DataTable.of(AdUnitIndex.class).get(unitItTable.getUnitId());
        if(null == adUnitObject){
            log.error("AdUnitItTable index error : {}", unitItTable.getUnitId());
        }
        Set<Long> value = new HashSet<>(Collections.singleton(unitItTable.getUnitId()));
        handleBinlogEvent(DataTable.of(UnitItIndex.class),unitItTable.getItTag(),value,type);
    }
    public static void handleLevel4(AdUnitKeywordTable unitKeywordTable,OpType type){
        if(type == OpType.UPDATE){
            log.error("keyword index do not support update");
            return;
        }
        AdUnitObject adUnitObject = DataTable.of(AdUnitIndex.class).get(unitKeywordTable.getUnitId());
        if(null == adUnitObject){
            log.error("AdUnitKeywordTable index error : {}", unitKeywordTable.getUnitId());
        }
        Set<Long> value = new HashSet<>(Collections.singleton(unitKeywordTable.getUnitId()));
        handleBinlogEvent(DataTable.of(UnitKeywordIndex.class),unitKeywordTable.getKeyword(),value,type);
    }

    /*
    心得：突然领悟到了面向接口编程的好处：先用接口抽象出一类操作（IndexAware），然后不同的对象(**Index)去实现该接口，重写方法定义自己的
    具体动作，当我们需要这些具体的对象(**Index)的时候，可以通过传入该接口，然后在对应方法（handleBinlogEvent）中实现不同的业务。
    那么如果没有这个接口的话，就得定义多个具体的方法了，造成代码冗余。
     */
    public static <K,V> void handleBinlogEvent(IndexAware<K,V> index, K key, V value, OpType type){
        switch (type){
            case ADD:
                index.add(key,value);
                break;
            case DELETE:
                index.delete(key,value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            default:
                break;
        }
    }
}
