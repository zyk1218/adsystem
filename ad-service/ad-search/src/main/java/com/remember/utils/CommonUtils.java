package com.remember.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
  * @author remember
  * @date 2020/4/23 16:40
  */
@Slf4j
public class CommonUtils {
    //computeIfAbsent和Supplier都是Java8的特性..前者是判断第一个参数对应的VALUE是否为空，是的话就将参数2作为VALUE存入，不是的话就直接返回VALUE。
    public static <K,V> V getOrCreate(K key, Map<K,V> map, Supplier<V> factory){
        return map.computeIfAbsent(key,k->factory.get());
    }

    public static String stringConcat(String ... args){
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : args){
            stringBuilder.append(s);
            stringBuilder.append("-");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    //'2020-01-01 20:00:00'  Thu Jan 02 04:00:00 SGT 2020
    public static Date parseStringDate (String stringDate){
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        try {
            return DateUtils.addHours(dateFormat.parse(stringDate),-8);
        } catch (ParseException e) {
            log.error("{} can not parse",stringDate);
            return null;
        }
    }
}
