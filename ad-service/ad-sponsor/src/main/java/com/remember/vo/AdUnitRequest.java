package com.remember.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
  * @author remember
  * @date 2020/4/20 16:52
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitRequest {
    private Long planId;
    private String unitName;

    private Integer positionType;
    private Long budget;

    public boolean creativeValidate(){
        return null != planId && !StringUtils.isEmpty(unitName) && positionType != null && null != budget;
    }
}
