package com.remember.controller;

import com.alibaba.fastjson.JSON;
import com.remember.entity.AdPlan;
import com.remember.exception.AdException;
import com.remember.service.IAdPlanService;
import com.remember.vo.AdPlanGetRequest;
import com.remember.vo.AdPlanRequest;
import com.remember.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
  * @author remember
  * @date 2020/4/20 19:26
  */
@Slf4j
@RestController
public class AdPlanOPController {
    private final IAdPlanService adPlanService;

    @Autowired
    public AdPlanOPController(IAdPlanService adPlanService) {
        this.adPlanService = adPlanService;
    }

    @PostMapping("/create/adPlan")
    public AdPlanResponse createAdPlan(@RequestBody AdPlanRequest request)throws AdException{
        log.info("ad-sponsor : create adPlan -> {}", JSON.toJSONString(request));
        return adPlanService.createAdPlan(request);
    }
    @DeleteMapping("/delete/adPlan")
    public void deleteAdPlan(@RequestBody AdPlanRequest request)throws AdException{
        log.info("ad-sponsor : delete adPlan -> {}", JSON.toJSONString(request));
        adPlanService.deleteAdPlan(request);
    }
    @PutMapping("/update/adPlan")
    public AdPlanResponse updateAdPlan(@RequestBody AdPlanRequest request)throws AdException{
        log.info("ad-sponsor : update adPlan -> {}", JSON.toJSONString(request));
        return adPlanService.updateAdPlan(request);
    }
    @PostMapping("/get/adPlan")
    public List<AdPlan> getAdPlan(@RequestBody AdPlanGetRequest request)throws AdException{
        log.info("ad-sponsor : find adPlan -> {}", JSON.toJSONString(request));
        return adPlanService.getAdPlansByIds(request);
    }
}
