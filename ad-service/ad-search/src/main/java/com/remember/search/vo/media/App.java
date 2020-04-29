package com.remember.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
  * @author remember
  * @date 2020/4/29 16:53
 * 应用的基本信息
  */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class App {
    //应用编码
    private String appCode;
    //应用名称
    private String appName;
    //应用包名
    private String packageName;
    //activity名称
    private String activityName;
}
