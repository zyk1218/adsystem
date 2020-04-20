package com.remember.dao;

import com.remember.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
  * @author remember
  * @date 2020/4/20 9:46
 * AdPlan DAO
  */
public interface AdPlanRepository extends JpaRepository <AdPlan,Long>{
    AdPlan findByIdAndUserId(Long id,Long userId);

    List<AdPlan> findAllByIdInAndUserId(List<Long> ids,Long userId);

    AdPlan findByUserIdAndPlanName(Long userId,String planName);

    List<AdPlan> findAllByPlanStatus(Integer status);
}
