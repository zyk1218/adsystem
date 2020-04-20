package com.remember.service.impl;

import com.remember.constant.Constants;
import com.remember.dao.AdPlanRepository;
import com.remember.dao.AdUnitRepository;
import com.remember.dao.unit_condition.AdUnitDistrictRepository;
import com.remember.dao.unit_condition.AdUnitItRepository;
import com.remember.dao.unit_condition.AdUnitKeywordRepository;
import com.remember.entity.AdPlan;
import com.remember.entity.AdUnit;
import com.remember.entity.unit_condition.AdUnitDistrict;
import com.remember.entity.unit_condition.AdUnitIt;
import com.remember.entity.unit_condition.AdUnitKeyword;
import com.remember.exception.AdException;
import com.remember.service.IAdUnitService;
import com.remember.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
  * @author remember
  * @date 2020/4/20 16:56
  */
@Service
public class AdUnitServiceImpl implements IAdUnitService{
    private final AdPlanRepository planRepository;
    private final AdUnitRepository unitRepository;
    private final AdUnitKeywordRepository unitKeywordRepository;
    private final AdUnitItRepository unitItRepository;
    private final AdUnitDistrictRepository districtRepository;

    @Autowired
    public AdUnitServiceImpl(AdPlanRepository planRepository, AdUnitRepository unitRepository, AdUnitKeywordRepository unitKeywordRepository, AdUnitItRepository unitItRepository, AdUnitDistrictRepository districtRepository) {
        this.planRepository = planRepository;
        this.unitRepository = unitRepository;
        this.unitKeywordRepository = unitKeywordRepository;
        this.unitItRepository = unitItRepository;
        this.districtRepository = districtRepository;
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

    @Override
    public AdUnitKeywordResponse createAdUnitKeyword(AdUnitKeywordRequest request) throws AdException {
        List<Long> unitIds = request.getUnitKeywords().stream().
                map(AdUnitKeywordRequest.UnitKeyword::getUnitId).
                collect(Collectors.toList());
        if(!isRelatedUnitExist(unitIds)){
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }
        List<Long> ids;//保存下面AdUnitKeyword的主键
        List<AdUnitKeyword> unitKeywords = new ArrayList<>();
        if(!CollectionUtils.isEmpty(request.getUnitKeywords())){
            request.getUnitKeywords().forEach(
                    i -> unitKeywords.add(new AdUnitKeyword(i.getUnitId(),i.getKeyword()))
            );
        }
        ids = unitKeywordRepository.saveAll(unitKeywords).stream().map(AdUnitKeyword::getId).collect(Collectors.toList());
        return new AdUnitKeywordResponse(ids);
    }

    @Override
    public AdUnitItResponse createAdUnitIt(AdUnitItRequest request) throws AdException {
        List<Long> unitIds = request.getUnitIts().stream().map(AdUnitItRequest.UnitIt::getUnitId).collect(Collectors.toList());
        if(!isRelatedUnitExist(unitIds)){
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }
        List<Long> ids;
        List<AdUnitIt> unitIts = new ArrayList<>();
        request.getUnitIts().stream().forEach(
                i -> unitIts.add(new AdUnitIt(i.getUnitId(),i.getItTag()))
        );
        ids = unitItRepository.saveAll(unitIts).stream().map(AdUnitIt::getId).collect(Collectors.toList());
        return new AdUnitItResponse(ids);
    }

    @Override
    public AdUnitDistrictResponse createAdUnitDistrict(AdUnitDistrictRequest request) throws AdException {
        List<Long> unitIds = request.getDistricts().stream().map(AdUnitDistrictRequest.unitDistrict::getUnitId).collect(Collectors.toList());
        if(!isRelatedUnitExist(unitIds)){
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }
        List<Long> ids;
        List<AdUnitDistrict> unitDistricts = new ArrayList<>();
        request.getDistricts().stream().forEach(
                i -> unitDistricts.add(new AdUnitDistrict(i.getUnitId(),i.getProvince(),i.getCity()))
        );
        ids = districtRepository.saveAll(unitDistricts).stream().map(AdUnitDistrict::getId).collect(Collectors.toList());
        return new AdUnitDistrictResponse(ids);
    }

    //校验传入进来的UnitIds是否合法
    public boolean isRelatedUnitExist(List<Long> unitIds){
        if(CollectionUtils.isEmpty(unitIds)){
            return false;
        }
        return unitRepository.findAllById(unitIds).size() == new HashSet<>(unitIds).size();
    }
}
