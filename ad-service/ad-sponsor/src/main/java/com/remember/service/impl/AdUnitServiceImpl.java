package com.remember.service.impl;

import com.remember.constant.Constants;
import com.remember.dao.AdPlanRepository;
import com.remember.dao.AdUnitRepository;
import com.remember.entity.AdPlan;
import com.remember.entity.AdUnit;
import com.remember.exception.AdException;
import com.remember.service.IAdUnitService;
import com.remember.vo.AdUnitRequest;
import com.remember.vo.AdUnitResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
  * @author remember
  * @date 2020/4/20 16:56
  */
@Service
public class AdUnitServiceImpl implements IAdUnitService{
    private final AdPlanRepository planRepository;
    private final AdUnitRepository unitRepository;

    @Autowired
    public AdUnitServiceImpl(AdPlanRepository planRepository, AdUnitRepository unitRepository) {
        this.planRepository = planRepository;
        this.unitRepository = unitRepository;
    }

    @Override
    public AdUnitResponse createAdUnit(AdUnitRequest request) throws AdException {
        if(!request.creativeValidate()){
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }
        Optional<AdPlan> plan = planRepository.findById(request.getPlanId());
        if(!plan.isPresent()){
            throw new AdException(Constants.ErrorMessage.CANNOT_FIND_RECORD);
        }
        AdUnit oldAdUnit = unitRepository.findByPlanIdAndUnitName(request.getPlanId(),request.getUnitName());
        if(oldAdUnit != null){
            throw new AdException(Constants.ErrorMessage.SAME_NAME_UNIT_ERROR);
        }
        AdUnit unit = unitRepository.save(new AdUnit(request.getPlanId(), request.getUnitName(), request.getPositionType(), request.getBudget()));
        return new AdUnitResponse(unit.getId(),request.getUnitName());
    }
}
