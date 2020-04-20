package com.remember.dao;

import com.remember.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
  * @author remember
  * @date 2020/4/20 9:40
 * AdUser Dao
  */
public interface AdUserRepository extends JpaRepository<AdUser,Long> {
    //eg:根据用户名查找用户
    AdUser findByUsername(String username);
}
