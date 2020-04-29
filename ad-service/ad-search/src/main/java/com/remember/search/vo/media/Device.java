package com.remember.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
  * @author remember
  * @date 2020/4/29 16:58
 * 设备信息
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device{
    //设备ID
    private String deviceCode;
    //mac
    private String mac;
    //ip
    private String ip;
    //机型编码
    private String model;
    //分辨率
    private String displaySize;
    //屏幕尺寸
    private String screenSize;
    //设备序列号
    private String serialName;
}
