package com.remember.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
  * @author remember
  * @date 2020/4/20 17:28
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitDistrictRequest {

    private List<unitDistrict> districts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class unitDistrict{
        private Long unitId;
        private String province;
        private String city;
    }
}
