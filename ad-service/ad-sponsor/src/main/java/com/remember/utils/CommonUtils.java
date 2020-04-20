package com.remember.utils;

import com.remember.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
  * @author remember
  * @date 2020/4/20 10:48
 * 通用工具类
  */
public class CommonUtils {

    //时间格式
    private static String[] parsePatterns = {
            "yyyy-MM-dd","yyyy/MM/dd","yyyy.MM.dd"
    };



    /**
     * 对字符串加盐并转变成大写
     * @param value
     * @return
     */
    public static String md5(String value){
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    public static Date parseStringDate(String dateString) throws AdException{
        try{
            return DateUtils.parseDate(dateString,parsePatterns);
        }catch (Exception ex){
            throw new AdException(ex.getMessage());
        }

    }
}
