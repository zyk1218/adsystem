package com.remember.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
  * @author remember
  * @date 2020/4/20 11:59
 * 创建和更新AdPlan时的响应
  */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanResponse {
    private Long id;
    private String planName;
}
