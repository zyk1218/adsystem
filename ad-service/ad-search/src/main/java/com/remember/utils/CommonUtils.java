package com.remember.utils;

import java.util.Map;
import java.util.function.Supplier;

/**
  * @author remember
  * @date 2020/4/23 16:40
  */
public class CommonUtils {
    //computeIfAbsent和Supplier都是Java8的特性..前者是判断第一个参数对应的VALUE是否为空，是的话就将参数2作为VALUE存入，不是的话就直接返回VALUE。
    public static <K,V> V getOrCreate(K key, Map<K,V> map, Supplier<V> factory){
        return map.computeIfAbsent(key,k->factory.get());
    }
}
