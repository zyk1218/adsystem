package com.remember.index.interest;

import com.remember.index.IndexAware;
import com.remember.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
  * @author remember
  * @date 2020/4/23 18:39
  */
@Slf4j
@Component
public class UnitItIndex implements IndexAware<String,Set<Long>>{

    private static Map<String,Set<Long>> itUnitMap;
    private static Map<Long,Set<String>> unitItMap;

    static {
        itUnitMap = new ConcurrentHashMap<>();
        unitItMap = new ConcurrentHashMap<>();
    }
    @Override
    public Set<Long> get(String key) {
        return itUnitMap.get(key);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("it index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("unitItIndex before delete : {}",unitItMap);
        Set<Long> unitIds = CommonUtils.getOrCreate(key,itUnitMap,ConcurrentSkipListSet::new);
        unitIds.removeAll(value);
        for(Long unitId : value){
            Set<String> its = CommonUtils.getOrCreate(unitId,unitItMap,ConcurrentSkipListSet::new);
            its.remove(key);
        }

    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("unitItIndex before add : {}",unitItMap);
        Set<Long> unitId = CommonUtils.getOrCreate(key,itUnitMap, ConcurrentSkipListSet::new);
        unitId.addAll(value); //将要添加的IT对应的ID存入原本的ID集合中。
        for(Long id : value){
            Set<String> its = CommonUtils.getOrCreate(id,unitItMap,ConcurrentSkipListSet::new);
            its.add(key);//给每一个对应的ID添加它新增的IT
        }
        log.info("UnitItIndex after add : {}",unitItMap);
    }

    public boolean match(Long unitId,List<String> itTags){
        if(unitItMap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitItMap.get(unitId))){
            return CollectionUtils.isSubCollection(itTags,unitItMap.get(unitId));
        }
        return false;
    }
}
