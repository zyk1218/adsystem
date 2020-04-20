package com.remember.service.impl;

import com.remember.constant.Constants;
import com.remember.dao.AdUserRepository;
import com.remember.entity.AdUser;
import com.remember.exception.AdException;
import com.remember.service.IUserService;
import com.remember.utils.CommonUtils;
import com.remember.vo.CreateUserRequest;
import com.remember.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
  * @author remember
  * @date 2020/4/20 10:26
 * UserService实现类
  */
@Slf4j
@Service
public class UserServiceImpl implements IUserService{

    /**
      * @author remember
      * @date 2020/4/20 10:30
     *
     *
     * @Autowired
    private AdUserRepository adUserRepository;
     问题：为什么Spring推荐我使用构造器进行依赖注入：
     因为Field注入的话会引起循环依赖，此外如果该属性不使用的话我们无法发现是否NPE，此外IOC容器之外无法复用该属性。
     之前在看如何解决Bean的循环依赖的时候提到了如果是构造器注入的话会抛出异常BeanCurrentlyInCreationException，从而
     避免循环依赖
      */
    private final AdUserRepository adUserRepository;

    @Autowired
    public UserServiceImpl(AdUserRepository adUserRepository) {
        this.adUserRepository = adUserRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {
        if(!request.validate()){
            throw new AdException(Constants.ErrorMessage.REQUEST_PARAM_ERROR);
        }
        AdUser oldUser = adUserRepository.findByUsername(request.getUserName());
        if(oldUser != null){
            throw new AdException(Constants.ErrorMessage.SAME_NAME_ERROR);
        }

        AdUser newUser = new AdUser(request.getUserName(), CommonUtils.md5(request.getUserName()));
        return new CreateUserResponse(newUser.getId(),newUser.getUsername(),newUser.getToken(),newUser.getCreateTime(),newUser.getUpdateTime());
    }
}
