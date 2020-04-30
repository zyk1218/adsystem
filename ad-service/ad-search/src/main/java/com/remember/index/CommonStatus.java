package com.remember.index;

import lombok.Getter;
/**
  * @author remember
  * @date 2020/4/30 7:57
  */
@Getter
public enum  CommonStatus {
    VALID(1,"有效状态"),
    INVALID(2,"无效状态");

    private Integer status;
    private String desc;

    CommonStatus(Integer status,String desc){
        this.status = status;
        this.desc = desc;
    }
}
