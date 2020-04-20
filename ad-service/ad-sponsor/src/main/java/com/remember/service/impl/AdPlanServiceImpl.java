package com.remember.service.impl;

import com.remember.constant.CommonStatus;
import com.remember.constant.Constants;
import com.remember.dao.AdPlanRepository;
import com.remember.dao.AdUserRepository;
import com.remember.entity.AdPlan;
import com.remember.entity.AdUser;
import com.remember.exception.AdException;
import com.remember.service.IAdPlanService;
import com.remember.utils.CommonUtils;
import com.remember.vo.AdPlanGetRequest;
import com.remember.vo.AdPlanRequest;
import com.remember.vo.AdPlanResponse;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
  * @author remember
  * @date 2020/4/20 12:19
 * AdPlan 实现类
  */
@Service
public class AdPlanServiceImpl implements IAdPlanService{

    private final AdUserRepository userRepository;

    private final AdPlanRepository planRepository;

    @Autowired
    public AdPlanServiceImpl(AdPlanRepository planRepository, AdUserRepository userRepository) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
        //判断参数的合法性
        if(!request.createValidate()){
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }
        //确保与AdPlan相关的User是存在的
        Optional<AdUser> user = userRepository.findById(request.getUserId());
        if(!user.isPresent()){
            throw new AdException(Constants.ErrorMessage.CANNOT_FIND_RECORD);
        }
        //确保该AdPlan之前未被创建过
        AdPlan adPlan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if(adPlan != null){
            throw new AdException(Constants.ErrorMessage.SAME_NAME_PLAN_ERROR);
        }
        AdPlan newAdPlan = planRepository.save(new AdPlan(request.getUserId(),request.getPlanName(),
                CommonUtils.parseStringDate(request.getStartDate()),CommonUtils.parseStringDate(request.getEndDate())));
        return new AdPlanResponse(request.getId(),request.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlansByIds(AdPlanGetRequest request) throws AdException {
        //首先判断参数的合法性
        if(!request.validate()){
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }
        return planRepository.findAllByIdInAndUserId(request.getIds(),request.getUserId());
    }

    @Override
    @Transactional
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {
        //首先判断参数合法性
        if(!request.updateValidate()){
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }
        //判断计划是否存在
        AdPlan oldPlan = planRepository.findByIdAndUserId(request.getId(),request.getUserId());
        if(oldPlan == null){
            throw new AdException(Constants.ErrorMessage.CANNOT_FIND_RECORD);
        }
        if(request.getEndDate() != null){
            oldPlan.setEndDate(CommonUtils.parseStringDate(request.getEndDate()));
        }
        if(request.getStartDate() != null){
            oldPlan.setStartTime(CommonUtils.parseStringDate(request.getStartDate()));
        }
        if(request.getPlanName() != null){
            oldPlan.setPlanName(request.getPlanName());
        }
        if(request.getUserId() != null){
            oldPlan.setUserId(request.getUserId());
        }
        oldPlan.setUpdateTime(new Date());
        planRepository.save(oldPlan);
        return new AdPlanResponse(request.getId(),request.getPlanName());
    }

    @Override
    @Transactional
    public void deleteAdPlan(AdPlanRequest request) throws AdException {
        if(!request.deleteValidate()){
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }
        AdPlan plan = planRepository.findByIdAndUserId(request.getId(),request.getUserId());
        if(plan == null){
            throw new AdException(Constants.ErrorMessage.CANNOT_FIND_RECORD);
        }
        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdateTime(new Date());
        planRepository.save(plan);

    }
}
