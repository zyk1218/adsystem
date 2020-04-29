package com.remember.search.vo;

import com.remember.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * @author remember
  * @date 2020/4/29 17:49
  */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    //<广告位的编码，List<创意>
    public Map<String,List<Creative>> adSlot2Ads = new HashMap<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Creative{
        private Long adId;
        private String adUrl;
        private Integer width;
        private Integer height;
        private Integer type;
        private Integer materialType;

        //展示监测URL
        private List<String> showMonitorUrl = Arrays.asList("www.imooc.com","www.imooc.com");
        //点击监测URL
        private List<String> clickMonitorUrl = Arrays.asList("www.imooc.com","www.imooc.com");
    }
    public static Creative convert(CreativeObject object){
        Creative creative = new Creative();
        creative.setAdId(object.getAdId());
        creative.setAdUrl(object.getAdUrl());
        creative.setHeight(object.getHeight());
        creative.setWidth(object.getWidth());
        creative.setMaterialType(object.getMaterialType());
        creative.setType(object.getType());
        return creative;
    }

}
