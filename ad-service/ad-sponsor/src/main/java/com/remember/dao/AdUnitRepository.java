package com.remember.dao;

import com.remember.entity.AdUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
  * @author remember
  * @date 2020/4/20 9:56
 * AdUnit Dao
  */
public interface AdUnitRepository extends JpaRepository<AdUnit,Long>{
    AdUnit findByPlanIdAndUnitName(Integer planId,String unitName);

    List<AdUnit> findAllByUnitStatus(Integer unitStatus);
}
