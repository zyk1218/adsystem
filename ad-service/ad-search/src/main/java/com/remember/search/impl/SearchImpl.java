package com.remember.search.impl;

import com.remember.index.DataTable;
import com.remember.index.adunit.AdUnitIndex;
import com.remember.search.ISearch;
import com.remember.search.vo.SearchRequest;
import com.remember.search.vo.SearchResponse;
import com.remember.search.vo.feature.DistrictFeature;
import com.remember.search.vo.feature.FeatureRelation;
import com.remember.search.vo.feature.ItFeature;
import com.remember.search.vo.feature.KeywordFeature;
import com.remember.search.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
  * @author remember
  * @date 2020/4/29 21:42
  */
@Slf4j
@Component
public class SearchImpl implements ISearch{
    @Override
    public SearchResponse fetchAds(SearchRequest request) {
        //请求的广告位信息
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();
        //请求匹配信息
        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        ItFeature itFeature = request.getFeatureInfo().getItFeature();
        FeatureRelation relation = request.getFeatureInfo().getRelation();
        //构造响应对象
        SearchResponse searchResponse = new SearchResponse();
        Map<String,List<SearchResponse.Creative>> adSlot2Ads = searchResponse.getAdSlot2Ads();
        for (AdSlot adSlot : adSlots) {
            Set<Long> targetUnitIdSet;
            //根据流量类型获取初始AdUnit
            Set<Long> adUnitIdSet = DataTable.of(AdUnitIndex.class).match(adSlot.getPositionType());
        }
    }
}
