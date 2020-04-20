package com.remember.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
  * @author remember
  * @date 2020/4/20 10:16
 * 创建User时需要提供给广告投放系统的数据
  */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private String userName;

    //验证参数是否有效
    public boolean validate(){
        return !StringUtils.isEmpty(userName);
    }
}
