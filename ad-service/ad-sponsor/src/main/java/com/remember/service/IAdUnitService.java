package com.remember.service;

import com.remember.entity.unit_condition.AdUnitDistrict;
import com.remember.entity.unit_condition.AdUnitIt;
import com.remember.entity.unit_condition.AdUnitKeyword;
import com.remember.exception.AdException;
import com.remember.vo.*;

/**
  * @author remember
  * @date 2020/4/20 16:51
 * AdUnit
  */
public interface IAdUnitService {
    AdUnitResponse createAdUnit(AdUnitRequest request) throws AdException;

    AdUnitKeywordResponse createAdUnitKeyword(AdUnitKeywordRequest request) throws AdException;

    AdUnitItResponse createAdUnitIt(AdUnitItRequest request) throws AdException;

    AdUnitDistrictResponse createAdUnitDistrict(AdUnitDistrictRequest request) throws AdException;
}
