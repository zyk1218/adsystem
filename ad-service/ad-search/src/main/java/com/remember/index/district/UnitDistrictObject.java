package com.remember.index.district;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
  * @author remember
  * @date 2020/4/23 19:16
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDistrictObject {
    private Long unitId;
    private String province;
    private String city;
}
