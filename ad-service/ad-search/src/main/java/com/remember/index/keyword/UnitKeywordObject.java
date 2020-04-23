package com.remember.index.keyword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
  * @author remember
  * @date 2020/4/22 12:32
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitKeywordObject {

    private Long unitId;
    private String keyword;

    void update(UnitKeywordObject newObject){
        if(null != newObject.getUnitId()){
            this.unitId = newObject.getUnitId();
        }
        if(null != newObject.getKeyword()){
            this.keyword = newObject.getKeyword();
        }
    }

}
