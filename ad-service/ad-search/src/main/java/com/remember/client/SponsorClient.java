package com.remember.client;

import com.remember.client.vo.AdPlan;
import com.remember.client.vo.AdPlanGetRequest;
import com.remember.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
  * @author remember
  * @date 2020/4/22 10:20
  */
@Component
@FeignClient(value = "eureka-client-ad-sponsor",fallback = SponsorClientHystrix.class) //该注解声明了要调用的微服务的名称
                                                                                        //fallback是服务降级。
public interface SponsorClient {

    @RequestMapping(value = "/ad-sponsor/get/adPlan",method = RequestMethod.POST)
    CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request);
}
