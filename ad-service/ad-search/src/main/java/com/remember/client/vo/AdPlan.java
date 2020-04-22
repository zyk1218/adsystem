package com.remember.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
  * @author remember
  * @date 2020/4/21 11:44
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdPlan {
    private Long id;
    private Long userId;
    private String planName;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;
    private Date createDate;
    private Date updateDate;
}
