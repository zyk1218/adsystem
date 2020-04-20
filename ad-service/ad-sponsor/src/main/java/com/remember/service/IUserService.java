package com.remember.service;

import com.remember.exception.AdException;
import com.remember.vo.CreateUserRequest;
import com.remember.vo.CreateUserResponse;

/**
  * @author remember
  * @date 2020/4/20 10:22
 * 主要用于新建用户
  */
public interface IUserService {
    //创建用户
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
