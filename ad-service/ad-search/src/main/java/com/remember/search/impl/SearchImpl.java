package com.remember.search.impl;

import com.alibaba.fastjson.JSON;
import com.remember.index.CommonStatus;
import com.remember.index.DataTable;
import com.remember.index.adunit.AdUnitIndex;
import com.remember.index.adunit.AdUnitObject;
import com.remember.index.creative.CreativeIndex;
import com.remember.index.creative.CreativeObject;
import com.remember.index.creativeunit.CreativeUnitIndex;
import com.remember.index.district.UnitDistrictIndex;
import com.remember.index.interest.UnitItIndex;
import com.remember.index.keyword.UnitKeywordIndex;
import com.remember.search.ISearch;
import com.remember.search.vo.SearchRequest;
import com.remember.search.vo.SearchResponse;
import com.remember.search.vo.feature.DistrictFeature;
import com.remember.search.vo.feature.FeatureRelation;
import com.remember.search.vo.feature.ItFeature;
import com.remember.search.vo.feature.KeywordFeature;
import com.remember.search.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;

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
            //根据请求匹配信息进行再过滤
            if(relation == FeatureRelation.AND){
                filterDistrictFeature(adUnitIdSet,districtFeature);
                filterItFeature(adUnitIdSet,itFeature);
                filterKeywordFeature(adUnitIdSet,keywordFeature);
                targetUnitIdSet = adUnitIdSet;
            }else{
                targetUnitIdSet = getORRelationUnitIds(adUnitIdSet,keywordFeature,itFeature,districtFeature);
            }
            List<AdUnitObject> unitObjects = DataTable.of(AdUnitIndex.class).fetch(targetUnitIdSet);
            //根据推广单元和推广计划的状态进行再过滤
            filterAdUnitAndPlanStatus(unitObjects,CommonStatus.VALID);
            //根据推广单元获取到创意
            List<Long> adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
            List<CreativeObject> creatives = DataTable.of(CreativeIndex.class).fetch(adIds);
            //通过adSlot对创意进行过滤
            filterCreativeByAdSlot(creatives,adSlot.getHeight(),adSlot.getWidth(),adSlot.getType());
            adSlot2Ads.put(adSlot.getAdSlotCode(),buildCreativeResponse(creatives));
        }
        log.info("fetch ads : {} - {}", JSON.toJSONString(request),JSON.toJSONString(searchResponse));
        return searchResponse;
    }
    private void filterKeywordFeature(Collection<Long> adUnitIds,KeywordFeature keywordFeature){
        if(CollectionUtils.isEmpty(adUnitIds)){
            return;
        }
        if(CollectionUtils.isNotEmpty(keywordFeature.getKeywords())){
            CollectionUtils.filter(adUnitIds,adUnitId -> DataTable.of(UnitKeywordIndex.class).match(adUnitId,keywordFeature.getKeywords()));
        }
    }

    private void filterDistrictFeature(Collection<Long> adUnitIds,DistrictFeature districtFeature){
        if(CollectionUtils.isEmpty(adUnitIds)){
            return;
        }
        if(CollectionUtils.isNotEmpty(districtFeature.getDistricts())){
            CollectionUtils.filter(adUnitIds,adUnitId -> DataTable.of(UnitDistrictIndex.class).match(adUnitId,districtFeature.getDistricts()));
        }
    }

    private void filterItFeature(Collection<Long> adUnitIds,ItFeature itFeature){
        if(CollectionUtils.isEmpty(adUnitIds)){
            return;
        }
        if(CollectionUtils.isNotEmpty(itFeature.getIts())){
            CollectionUtils.filter(adUnitIds,adUnitId -> DataTable.of(UnitItIndex.class).match(adUnitId,itFeature.getIts()));
        }
    }

    private Set<Long> getORRelationUnitIds(Set<Long> adUnitIdSet,KeywordFeature keywordFeature,ItFeature itFeature,DistrictFeature districtFeature){
        if(CollectionUtils.isEmpty(adUnitIdSet)){
            return Collections.emptySet();
        }
        //保存副本
        Set<Long> keywordUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> districtUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> itUnitIdSet = new HashSet<>(adUnitIdSet);

        filterDistrictFeature(districtUnitIdSet,districtFeature);
        filterItFeature(itUnitIdSet,itFeature);
        filterKeywordFeature(keywordUnitIdSet,keywordFeature);

        return new HashSet<>(CollectionUtils.union(CollectionUtils.union(keywordUnitIdSet,districtUnitIdSet),itUnitIdSet));

    }

    private void filterAdUnitAndPlanStatus(List<AdUnitObject> unitObjects, CommonStatus status){
        if(CollectionUtils.isEmpty(unitObjects)){
            return;
        }
        CollectionUtils.filter(unitObjects,unitObject->unitObject.getUnitStatus()
                .equals(status.getStatus()) && unitObject.getAdPlanObject().getPlanStatus().equals(status.getStatus()));
    }

    private void filterCreativeByAdSlot(List<CreativeObject> creativeObjects,Integer height,Integer width,List<Integer> type){
        if(CollectionUtils.isEmpty(creativeObjects)){
            return;
        }
        CollectionUtils.filter(creativeObjects,creativeObject -> creativeObject.getAuditStatus().equals(CommonStatus.VALID.getStatus())
                && creativeObject.getHeight().equals(height) && creativeObject.getWidth().equals(width) && type.contains(creativeObject.getType()));
    }

    private List<SearchResponse.Creative> buildCreativeResponse(List<CreativeObject> creativeObjects){
        if(CollectionUtils.isEmpty(creativeObjects)){
            return Collections.emptyList();
        }
        CreativeObject creativeObject = creativeObjects.get(Math.abs(new Random().nextInt()) % creativeObjects.size());
        return Collections.singletonList(SearchResponse.convert(creativeObject));
    }
}
