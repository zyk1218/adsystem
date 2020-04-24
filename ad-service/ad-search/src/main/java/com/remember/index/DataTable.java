package com.remember.index;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
  * @author remember
  * @date 2020/4/23 20:19
 * 索引服务类缓存的实现
 * ApplicationContextAware接口：表示该类需要应用的上下文
 * PriorityOrdered接口：声明后，Spring会先加载声明了先后顺序的Bean.声明这个接口是因为缓存的加载应该是要优先于其他
 * 服务的
  */
@Component
public class DataTable implements ApplicationContextAware,PriorityOrdered{

    private static ApplicationContext applicationContext;

    private static final Map<Class,Object> dataTableMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    @SuppressWarnings("all")
    public static <T> T of(Class<T> clazz){
        T instance = (T) dataTableMap.get(clazz);
        if(instance != null){
            return instance;
        }
        dataTableMap.put(clazz,bean(clazz));
        return (T)dataTableMap.get(clazz);

    }

    @SuppressWarnings("all")
    private static <T> T bean(String beanName){
        return (T) applicationContext.getBean(beanName);
    }

    @SuppressWarnings("all")
    private static <T> T bean(Class clazz){
        return (T) applicationContext.getBean(clazz);
    }
}
