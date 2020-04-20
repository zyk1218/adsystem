package com.remember.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
  * @author remember
  * @date 2020/4/20 10:48
 * 通用工具类
  */
public class CommonUtils {
    public static String md5(String value){
        return DigestUtils.md5Hex(value).toUpperCase();
    }
}
