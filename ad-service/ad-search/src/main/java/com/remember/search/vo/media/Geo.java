package com.remember.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
  * @author remember
  * @date 2020/4/29 16:55
 * 地理位置信息
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Geo {
    private Float latitude; //维度
    private Float longitude; //经度
    private String city;
    private String province;
}
