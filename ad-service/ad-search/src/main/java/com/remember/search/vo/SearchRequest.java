package com.remember.search.vo;

import com.remember.search.vo.feature.DistrictFeature;
import com.remember.search.vo.feature.FeatureRelation;
import com.remember.search.vo.feature.ItFeature;
import com.remember.search.vo.feature.KeywordFeature;
import com.remember.search.vo.media.AdSlot;
import com.remember.search.vo.media.App;
import com.remember.search.vo.media.Device;
import com.remember.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

/**
  * @author remember
  * @date 2020/4/29 16:42
  */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    //媒体方的请求标识
    private String mediaId;
    //请求基本信息
    private RequestInfo requestInfo;
    //请求匹配信息
    private FeatureInfo featureInfo;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class RequestInfo{
        private String requestId;

        private List<AdSlot> adSlots;
        private App app;
        private Device device;
        private Geo geo;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class FeatureInfo{
        private DistrictFeature districtFeature;
        private ItFeature itFeature;
        private KeywordFeature keywordFeature;
        private FeatureRelation relation = FeatureRelation.AND;
    }
}
