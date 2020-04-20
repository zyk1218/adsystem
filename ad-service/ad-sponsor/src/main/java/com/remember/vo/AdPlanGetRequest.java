package com.remember.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
  * @author remember
  * @date 2020/4/20 12:01
 * 用于获取AdPlan
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPlanGetRequest {
    private List<Long> ids;
    private Long userId;

    public boolean validate(){
        return !CollectionUtils.isEmpty(ids) && userId != null;
    }
}
