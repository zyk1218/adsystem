package com.remember.service;

import com.remember.entity.AdPlan;
import com.remember.exception.AdException;
import com.remember.vo.AdPlanGetRequest;
import com.remember.vo.AdPlanRequest;
import com.remember.vo.AdPlanResponse;

import java.util.List;

/**
  * @author remember
  * @date 2020/4/20 12:05
 * AdPlan
  */
public interface IAdPlanService {

    /**
     * 创建推广计划
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 批量获取推广计划
     * @param request
     * @return
     * @throws AdException
     */
    List<AdPlan> getAdPlansByIds(AdPlanGetRequest request) throws AdException;

    /**
     * 更新推广计划
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 删除推广计划
     * @param request
     * @throws AdException
     */
    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
