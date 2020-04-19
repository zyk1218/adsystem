package com.remember.constant;

import lombok.Getter;

/**
  * @author remember
  * @date 2020/4/19 13:31
 * 物料类型枚举类
  */

@Getter
public enum CreativeMetrialType {
    JPG(1,"jpg"),
    BMP(2,"bmp"),

    MP4(3,"mp4"),
    AVI(4,"avi"),

    TXT(5,"txt");


    private Integer type;
    private String desc;

    CreativeMetrialType(Integer type,String desc){
        this.type = type;
        this.desc = desc;
    }
}
