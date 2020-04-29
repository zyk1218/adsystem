package com.remember.sender.index;

import com.alibaba.fastjson.JSON;
import com.remember.client.vo.AdPlan;
import com.remember.dump.table.AdCreativeTable;
import com.remember.dump.table.AdCreativeUnitTable;
import com.remember.dump.table.AdPlanTable;
import com.remember.dump.table.AdUnitTable;
import com.remember.handler.AdLevelDataHandler;
import com.remember.index.DataLevel;
import com.remember.mysql.constant.Constant;
import com.remember.mysql.dto.MysqlRowData;
import com.remember.sender.ISender;
import com.remember.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
  * @author remember
  * @date 2020/4/29 12:10
  */
@Slf4j
@Component("indexSender")
public class IndexSender implements ISender {
    @Override
    public void sender(MysqlRowData rowData) {
        String level = rowData.getLevel().toString();
        if(DataLevel.LEVEL2.getLevel().equals(level)){
            Level2RowData(rowData);
        }else if(DataLevel.LEVEL3.getLevel().equals(level)){
            Level3RowData(rowData);
        }else if(DataLevel.LEVEL4.getLevel().equals(level)){

        }else{
            log.error("MysqlRowData ERROR :{}", JSON.toJSON(rowData));
        }
    }



    private void Level2RowData(MysqlRowData rowData) {
        if(rowData.getTableName().equals(Constant.AD_PLAN_TABLE_INFO.TABLE_NAME)){
            List<AdPlanTable> planTables = new ArrayList<>();
            for(Map<String,String> fieldValueMap : rowData.getFieldValueMap()){
                AdPlanTable adPlanTable = new AdPlanTable();
                fieldValueMap.forEach((k,v) -> {
                    switch (k){
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_ID : adPlanTable.setId(Long.valueOf(v));
                        break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_USER_ID : adPlanTable.setUserId(Long.valueOf(v));
                        break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_PLAN_STATUS : adPlanTable.setPlanStatus(Integer.valueOf(v));
                        break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_START_DATE : adPlanTable.setStartDate(CommonUtils.parseStringDate(v));
                        break;
                        case Constant.AD_PLAN_TABLE_INFO.COLUMN_END_DATE : adPlanTable.setEndDate(CommonUtils.parseStringDate(v));
                        break;
                    }
                });
                planTables.add(adPlanTable);
            }
            planTables.forEach( (p) -> AdLevelDataHandler.handleLevel2(p,rowData.getOpType()));
        } else if(rowData.getTableName().equals(Constant.AD_CREATIVE_TABLE_INFO.TABLE_NAME)){
            List<AdCreativeTable> creativeTables = new ArrayList<>();
            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                AdCreativeTable creativeTable = new AdCreativeTable();
                fieldValueMap.forEach((k,v) ->{
                    switch (k){
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_ID : creativeTable.setAdId(Long.valueOf(v));
                        break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_TYPE : creativeTable.setType(Integer.valueOf(v));
                        break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_MATERIAL_TYPE : creativeTable.setMaterialType(Integer.valueOf(v));
                        break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_HEIGHT : creativeTable.setHeight(Integer.valueOf(v));
                        break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_WIDTH : creativeTable.setWidth(Integer.valueOf(v));
                        break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_AUDIT_STATUS : creativeTable.setAuditStatus(Integer.valueOf(v));
                        break;
                        case Constant.AD_CREATIVE_TABLE_INFO.COLUMN_URL : creativeTable.setAdUrl(v);
                        break;
                    }
                });
                creativeTables.add(creativeTable);
            }
            creativeTables.forEach((p)-> AdLevelDataHandler.handleLevel2(p,rowData.getOpType()));
        }
    }
    private void Level3RowData(MysqlRowData rowData) {
        if(rowData.getTableName().equals(Constant.AD_CREATIVE_UNIT_TABLE_INFO.TABLE_NAME)){
            List<AdCreativeUnitTable> adCreativeUnitTables = new ArrayList<>();
            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                AdCreativeUnitTable adCreativeUnitTable = new AdCreativeUnitTable();
                fieldValueMap.forEach((k,v) ->{
                    switch (k){
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_CREATIVE_ID : adCreativeUnitTable.setAdId(Long.valueOf(v));
                        break;
                        case Constant.AD_CREATIVE_UNIT_TABLE_INFO.COLUMN_UNIT_ID : adCreativeUnitTable.setUnitId(Long.valueOf(v));
                        break;
                    }
                });
                adCreativeUnitTables.add(adCreativeUnitTable);
            }
            adCreativeUnitTables.forEach(p -> AdLevelDataHandler.handleLevel3(p,rowData.getOpType()));
        }else if(rowData.getTableName().equals(Constant.AD_UNIT_TABLE_INFO.TABLE_NAME)){
            List<AdUnitTable> adUnitTables = new ArrayList<>();
            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                AdUnitTable adUnitTable = new AdUnitTable();
                fieldValueMap.forEach((k,v) ->{
                    switch (k){
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_ID : adUnitTable.setUnitId(Long.valueOf(v));
                        break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_PLAN_ID : adUnitTable.setPlanId(Long.valueOf(v));
                        break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_POSITION_TYPE : adUnitTable.setPositionType(Integer.valueOf(v));
                        break;
                        case Constant.AD_UNIT_TABLE_INFO.COLUMN_UNIT_STATUS : adUnitTable.setUnitStatus(Integer.valueOf(v));
                        break;
                    }
                });
                adUnitTables.add(adUnitTable);
            }
            adUnitTables.forEach( p -> AdLevelDataHandler.handleLevel3(p,rowData.getOpType()));
        }
    }
}
