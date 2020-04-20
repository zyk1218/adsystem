package com.remember.dao;

import com.remember.entity.Creative;
import org.springframework.data.jpa.repository.JpaRepository;

/**
  * @author remember
  * @date 2020/4/20 10:03
 * Creative Dao
  */
public interface CreativeRepository extends JpaRepository<Creative,Long> {


}
