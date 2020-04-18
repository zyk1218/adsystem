package com.remember.constant;

import lombok.Getter;

/**
  * @author remember
  * @date 2020/4/18 18:49
 * 枚举用户的一些状态。
  */

@Getter
public enum CommonStatus {
    VALID(1,"有效状态"),
    INVALID(0,"无效状态");

    private Integer status;
    private String desc;

    CommonStatus(Integer status,String desc){
        this.status = status;
        this.desc = desc;
    }
}
