package com.remember.service;

import com.remember.exception.AdException;
import com.remember.vo.CreativeRequest;
import com.remember.vo.CreativeResponse;

/**
  * @author remember
  * @date 2020/4/20 18:37
  */
public interface ICreativeService {
    CreativeResponse createCreative(CreativeRequest request) throws AdException;
}
