package com.remember.controller;

import com.alibaba.fastjson.JSON;
import com.remember.entity.unit_condition.AdUnitDistrict;
import com.remember.entity.unit_condition.AdUnitKeyword;
import com.remember.exception.AdException;
import com.remember.service.IAdUnitService;
import com.remember.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
  * @author remember
  * @date 2020/4/20 19:38
  */

@Slf4j
@RestController
public class AdUnitOPController {
    private final IAdUnitService adUnitService;

    @Autowired
    public AdUnitOPController(IAdUnitService adUnitService) {
        this.adUnitService = adUnitService;
    }

    @PostMapping("/create/adUnit")
    public AdUnitResponse createAdUnit(@RequestBody AdUnitRequest request) throws AdException{
        log.info("ad-sponsor : create adUnit -> {}", JSON.toJSONString(request));
        return adUnitService.createAdUnit(request);
    }
    @PostMapping("/create/unitKeyword")
    public AdUnitKeywordResponse createUnitKeyword(@RequestBody AdUnitKeywordRequest request) throws AdException{
        log.info("ad-sponsor : create adUnitKeyword -> {}", JSON.toJSONString(request));
        return adUnitService.createAdUnitKeyword(request);
    }
    @PostMapping("/create/unitIt")
    public AdUnitItResponse createUnitIt(@RequestBody AdUnitItRequest request) throws AdException{
        log.info("ad-sponsor : create adUnitIt -> {}", JSON.toJSONString(request));
        return adUnitService.createAdUnitIt(request);
    }
    @PostMapping("/create/unitDistrict")
    public AdUnitDistrictResponse createUnitDistrict(@RequestBody AdUnitDistrictRequest request) throws AdException{
        log.info("ad-sponsor : create adUnitDistrict -> {}", JSON.toJSONString(request));
        return adUnitService.createAdUnitDistrict(request);
    }
    @PostMapping("/create/creativeUnit")
    public CreativeUnitResponse createCreativeUnit(@RequestBody CreativeUnitRequest request) throws AdException{
        log.info("ad-sponsor : create CreativeUnit -> {}", JSON.toJSONString(request));
        return adUnitService.createCreativeUnit(request);
    }

}
