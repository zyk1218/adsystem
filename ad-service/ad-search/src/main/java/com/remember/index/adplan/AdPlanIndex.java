package com.remember.index.adplan;

import com.remember.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
  * @author remember
  * @date 2020/4/22 12:01
 * 索引实现类
 * AdPlan是一个正向索引，所以泛型应该是Long，和AdPlanObject
  */
@Slf4j
@Component
public class AdPlanIndex implements IndexAware<Long,AdPlanObject> {

    private static Map<Long,AdPlanObject> objectMap;//用于存储索引。

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public AdPlanObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void update(Long key, AdPlanObject value) {
        log.info("before update : {}",objectMap);
        AdPlanObject old = objectMap.get(key);
        if(old == null){
            objectMap.put(key,value);
        }else {
            old.update(value);
        }
        log.info("after update : {}",objectMap);
    }

    @Override
    public void delete(Long key, AdPlanObject value) {
        log.info("before delete : {}",objectMap);
        objectMap.remove(key);
        log.info("after delete : {}",objectMap);
    }

    @Override
    public void add(Long key, AdPlanObject value) {
        log.info("before add : {}",objectMap);
        objectMap.put(key,value);
        log.info("after add : {}",objectMap);
    }
}
