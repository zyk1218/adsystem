package com.remember.service.impl;

import com.remember.constant.Constants;
import com.remember.dao.CreativeRepository;
import com.remember.entity.Creative;
import com.remember.exception.AdException;
import com.remember.service.ICreativeService;
import com.remember.vo.CreativeRequest;
import com.remember.vo.CreativeResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
  * @author remember
  * @date 2020/4/20 18:46
  */
@Service
public class CreativeServiceImpl implements ICreativeService{

    private final CreativeRepository creativeRepository;

    @Autowired
    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponse createCreative(CreativeRequest request) throws AdException {
        if(!isValidate(request)){
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }
        Creative creative = creativeRepository.save(request.convertToEntity());
        return new CreativeResponse(creative.getName(),creative.getId());
    }

    //属性有很多，挑最重要的两个做检验。
    public boolean isValidate(CreativeRequest request){
        return !StringUtils.isEmpty(request.getName()) && request.getType() != null;
    }
}
