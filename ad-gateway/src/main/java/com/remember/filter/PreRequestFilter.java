package com.remember.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
  * @author remember
  * @date 2020/4/17 17:42
 * Zuul的大多数功能是靠过滤器实现的。
 *
 *
 * 该过滤器的功能是：记录请求的延迟
 *
 *
 */

@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter{
    /*
    定义过滤器的类型，过滤器一共有四种类型：Pre Routing Post Error
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /*
    定义Filter执行的顺序，数字越小优先级越高
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /*
    该过滤器是否要被执行
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /*
    Filter执行的具体操作
     */
    @Override
    public Object run() throws ZuulException {
        //由于该过滤器的功能是记录延迟，所以需要一个请求上下文：RequestContext
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set("startTime",System.currentTimeMillis());
        return null;
    }
}
