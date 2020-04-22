package com.remember.client;

import com.remember.client.vo.AdPlan;
import com.remember.client.vo.AdPlanGetRequest;
import com.remember.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
  * @author remember
  * @date 2020/4/22 10:38
 * SponsorClient的断路器，实际就是一个服务降级的过程
  */
@Component
public class SponsorClientHystrix implements SponsorClient{

    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>(-1,"sponsor eureka error");
    }
}
