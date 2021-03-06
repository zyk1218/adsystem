package com.remember.controller;

import com.alibaba.fastjson.JSON;
import com.remember.annotation.IgnoreResponseAdvice;
import com.remember.client.SponsorClient;
import com.remember.client.vo.AdPlan;
import com.remember.client.vo.AdPlanGetRequest;
import com.remember.search.ISearch;
import com.remember.search.vo.SearchRequest;
import com.remember.search.vo.SearchResponse;
import com.remember.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
  * @author remember
  * @date 2020/4/21 12:16
  */
@Slf4j
@RestController
public class SearchController {

    private final ISearch search;

    private final RestTemplate restTemplate;

    private final SponsorClient sponsorClient;

    @Autowired
    public SearchController(RestTemplate restTemplate, SponsorClient sponsorClient, ISearch search) {
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
        this.search = search;
    }
    @SuppressWarnings("all")
    @IgnoreResponseAdvice //不对该响应进行拦截。
    @PostMapping("/getAdPlansByRibbon")
    public CommonResponse<List<AdPlan>> getAdPlansByRibbon(@RequestBody AdPlanGetRequest request){
        log.info("ad-search : get adPlanByRibbon -> {}", JSON.toJSONString(request));
        return restTemplate.postForEntity("http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan",
                request,CommonResponse.class).getBody();
    }
    @SuppressWarnings("all")
    @IgnoreResponseAdvice //不对该响应进行拦截。
    @PostMapping("/getAdPlans")
    public CommonResponse<List<AdPlan>> getAdPlansByFeign(@RequestBody AdPlanGetRequest request){
        log.info("ad-search : get adPlanByFeign -> {}", JSON.toJSONString(request));
        return sponsorClient.getAdPlans(request);
    }

    @PostMapping("/fetchAds")
    public SearchResponse fetchAds(@RequestBody SearchRequest request){
        log.info("ad-search : fetchAds -> {}",JSON.toJSONString(request));
        return search.fetchAds(request);
    }



}
