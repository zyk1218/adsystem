package com.remember.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
  * @author remember
  * @date 2020/4/29 16:50
 * 广告位信息
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdSlot {
    //广告位编码
    private String adSlotCode;
    //流量类型
    private Integer positionType;
    //宽和高
    private Integer height;
    private Integer width;
    //物料类型
    private List<Integer> type;
    //最低出价
    private Integer minCpm;
}

