package com.remember.index.keyword;

import com.remember.index.IndexAware;
import com.remember.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.apache.commons.collections4.CollectionUtils;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
  * @author remember
  * @date 2020/4/22 12:33
 * UnitKeyword不同于AdPlan和AdUnit，后者是正向索引，而推广限制这些是倒排索引
  */
@Slf4j
@Component
public class UnitKeywordIndex implements IndexAware<String,Set<Long>>{

    private static Map<String,Set<Long>> keywordUnitMap;
    private static Map<Long,Set<String>> unitKeywordMap;

    static {
        keywordUnitMap = new ConcurrentHashMap<>();
        unitKeywordMap = new ConcurrentHashMap<>();
    }

    @Override
    public Set<Long> get(String key) {
        if(StringUtils.isEmpty(key)){
            return Collections.emptySet();
        }
        Set<Long> result = keywordUnitMap.get(key);
        if(result == null){
            return Collections.emptySet();
        }
        return result;
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("keyword index can not support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("unitKeywordMap before delete : {}",unitKeywordMap);
        Set<Long> unitIds = CommonUtils.getOrCreate(key,keywordUnitMap,ConcurrentSkipListSet::new);
        unitIds.removeAll(value);
        for(Long unitId : value){
            Set<String> keywordSet = CommonUtils.getOrCreate(unitId, unitKeywordMap, ConcurrentSkipListSet::new);
            keywordSet.remove(key);
        }
        log.info("unitKeywordMap after delete : {}",unitKeywordMap);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("keywordUnitMap before add : {}",keywordUnitMap);
        Set<Long> unitIdSet = CommonUtils.getOrCreate(key,keywordUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);
        log.info("keywordUnitMap after add : {}",keywordUnitMap);
        log.info("unitKeywordMap before add : {}",unitKeywordMap);
        for(Long unitId : value){
            Set<String> keywordSet = CommonUtils.getOrCreate(unitId,unitKeywordMap,ConcurrentSkipListSet::new);
            keywordSet.add(key);
        }
        log.info("unitKeywordMap after add : {}",unitKeywordMap);
    }

    //判断给出的关键词是否和推广单元匹配
    public boolean match(Long unitID, List<String> keywords){
        if(unitKeywordMap.containsKey(unitID) && CollectionUtils.isNotEmpty(unitKeywordMap.get(unitID))){
            Set<String> unitKeyword = unitKeywordMap.get(unitID); //unitKeyword原本索引中推广单元对应的关键词集合。
            return CollectionUtils.isSubCollection(keywords,unitKeyword);
        }
        return false;
    }
}
