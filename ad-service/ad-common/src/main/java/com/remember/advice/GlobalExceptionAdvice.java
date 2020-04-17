package com.remember.advice;

import com.remember.exception.AdException;
import com.remember.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
  * @author remember
  * @date 2020/4/17 19:24
 * 全局的异常处理，异常处理也是一种响应。
  */
@RestControllerAdvice
public class GlobalExceptionAdvice {
    //一旦发生异常，我们会收到两个参数，HttpServletRequest与AdException
    //为什么是这两个参数？：HttpServletRequest可以告诉我们是哪个请求抛出了异常；由于抛出异常后没有响应了就，所以需要把异常传入
    //关于注解@ExceptionHandler：这个注解告诉Spring它要处理的异常的类型
    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerException(HttpServletRequest request, AdException exception){
        CommonResponse<String> response = new CommonResponse<>(-1,"business error");
        response.setData(exception.getMessage());
        return response;
    }
}
