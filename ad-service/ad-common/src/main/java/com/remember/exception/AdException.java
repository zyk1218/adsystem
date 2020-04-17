package com.remember.exception;
/**
  * @author remember
  * @date 2020/4/17 19:21
 * 自定义的异常类，该类是Ad系统的异常集合。
  */
public class AdException extends Exception{
    public AdException(String message) {
        super(message);
    }
}
