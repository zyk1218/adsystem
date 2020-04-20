package com.remember.controller;

import com.alibaba.fastjson.JSON;
import com.remember.exception.AdException;
import com.remember.service.ICreativeService;
import com.remember.vo.CreativeRequest;
import com.remember.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
  * @author remember
  * @date 2020/4/20 19:52
  */
@Slf4j
@RestController
public class CreativeOPController {
    private final ICreativeService creativeService;

    @Autowired
    public CreativeOPController(ICreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @PostMapping("/create/creative")
    public CreativeResponse createCreative(@RequestBody CreativeRequest request) throws AdException{
        log.info("ad-sponsor : create creative -> {}", JSON.toJSONString(request));
        return creativeService.createCreative(request);

    }
}
