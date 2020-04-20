package com.remember.service;

import com.remember.exception.AdException;
import com.remember.vo.AdUnitRequest;
import com.remember.vo.AdUnitResponse;

/**
  * @author remember
  * @date 2020/4/20 16:51
 * AdUnit
  */
public interface IAdUnitService {
    AdUnitResponse createAdUnit(AdUnitRequest request) throws AdException;
}
