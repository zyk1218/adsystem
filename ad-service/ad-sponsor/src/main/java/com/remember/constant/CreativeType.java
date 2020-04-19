package com.remember.constant;

import lombok.Getter;

/**
  * @author remember
  * @date 2020/4/19 13:25
 * 用于说明创意的类型与物料
  */
@Getter
public enum CreativeType {
    IMAGE(1,"图片"),
    VIDEO(2,"视频"),
    TEXT(3,"文本");

    private int type;
    private String desc;

    CreativeType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
