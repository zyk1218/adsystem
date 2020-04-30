package com.remember.index.creative;

import com.remember.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
  * @author remember
  * @date 2020/4/23 19:32
  */
@Component
@Slf4j
public class CreativeIndex implements IndexAware<Long,CreativeObject> {

    private static Map<Long,CreativeObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public CreativeObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void update(Long key, CreativeObject value) {
        log.info("objectMap before update : {}",objectMap);
        CreativeObject old = objectMap.get(key);
        if(old == null){
            objectMap.put(key,value);
        }else{
            old.update(value);
        }
        log.info("objectMap after update : {}",objectMap);

    }

    @Override
    public void delete(Long key, CreativeObject value) {
        log.info("objectMap before delete : {}",objectMap);
        objectMap.remove(key);
        log.info("objectMap after delete : {}",objectMap);

    }

    @Override
    public void add(Long key, CreativeObject value) {
        log.info("objectMap before add : {}",objectMap);
        objectMap.put(key,value);
        log.info("objectMap after add : {}",objectMap);
    }

    public List<CreativeObject> fetch(Collection<Long> adIds){
        if(CollectionUtils.isEmpty(adIds)){
            return Collections.emptyList();
        }
        List<CreativeObject> creativeObjects = new ArrayList<>();
        adIds.forEach( u -> {
            CreativeObject creativeObject = objectMap.get(u);
            if(null == creativeObject){
                log.error("CreativeObject not found  : {}",u);
                return;
            }else{
                creativeObjects.add(creativeObject);
            }
        });
        return creativeObjects;
    }

}
