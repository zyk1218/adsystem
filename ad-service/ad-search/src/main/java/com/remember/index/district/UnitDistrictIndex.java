package com.remember.index.district;

import com.remember.index.IndexAware;
import com.remember.search.vo.feature.DistrictFeature;
import com.remember.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
  * @author remember
  * @date 2020/4/23 19:18
  */
@Slf4j
@Component
public class UnitDistrictIndex implements IndexAware<String,Set<Long>>{

    private static Map<String,Set<Long>> districtUnitMap;

    private static Map<Long,Set<String>> unitDistrictMap;

    static {
        districtUnitMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }
    @Override
    public Set<Long> get(String key) {
        return districtUnitMap.get(key);
    }

    @Override
    public void update(String key, Set<Long> value) {
        log.error("district index can't support update");
    }

    @Override
    public void delete(String key, Set<Long> value) {
        log.info("unitDistrictIndex before delete : {}",unitDistrictMap);
        Set<Long> unitIds = CommonUtils.getOrCreate(key,districtUnitMap,ConcurrentSkipListSet::new);
        unitIds.removeAll(value);
        for(Long unitId : value){
            Set<String> districts = CommonUtils.getOrCreate(unitId,unitDistrictMap,ConcurrentSkipListSet::new);
            districts.remove(key);
        }
        log.info("unitDistrictIndex after delete : {}",unitDistrictMap);
    }

    @Override
    public void add(String key, Set<Long> value) {
        log.info("unitDistrictIndex before add : {}",unitDistrictMap);
        Set<Long> unitIds = CommonUtils.getOrCreate(key,districtUnitMap, ConcurrentSkipListSet::new);
        unitIds.addAll(value);
        for(Long unitId : value){
            Set<String> districts = CommonUtils.getOrCreate(unitId,unitDistrictMap,ConcurrentSkipListSet::new);
            districts.add(key);
        }
        log.info("unitDistrictIndex after add : {}",unitDistrictMap);
    }

    public boolean match(Long adUnitID, List<DistrictFeature.ProvinceAndCity> districts){
        if(unitDistrictMap.containsKey(adUnitID) && CollectionUtils.isNotEmpty(unitDistrictMap.get(adUnitID))){
            Set<String> unitDistricts = unitDistrictMap.get(adUnitID);
            List<String> targetDistricts = districts.stream().map(d-> CommonUtils.stringConcat(d.getProvince(),d.getCity())).collect(Collectors.toList());
            return CollectionUtils.isSubCollection(targetDistricts,unitDistricts);
        }
        return false;
    }
}
