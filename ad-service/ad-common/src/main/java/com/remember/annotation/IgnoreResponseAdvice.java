package com.remember.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
  * @author remember
  * @date 2020/4/17 18:54
 * 该注解是服务于advice包下的CommonResponseDataAdvice类的，用于指明CommonResponseDataAdvice类对哪些响应不进行拦截。
 * 用法：将该注解声明在类或方法上，当判断到某一个类或方法使用了该注解，那么该类或方法的响应就不会被拦截。
  */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseAdvice {

}
