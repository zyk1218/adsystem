package com.remember.controller;

import com.alibaba.fastjson.JSON;
import com.remember.exception.AdException;
import com.remember.service.IUserService;
import com.remember.vo.CreateUserRequest;
import com.remember.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
  * @author remember
  * @date 2020/4/20 19:17
  */
@Slf4j
@RestController
public class UserOPController {
    private final IUserService userService;

    @Autowired
    public UserOPController(IUserService userService) {
        this.userService = userService;
    }
    @PostMapping("/create/user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request)throws AdException{
        log.info("ad-sponsor : create user -> {}", JSON.toJSONString(request));
        return userService.createUser(request);
    }
}
