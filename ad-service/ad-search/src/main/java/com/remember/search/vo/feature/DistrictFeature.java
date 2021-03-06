package com.remember.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
  * @author remember
  * @date 2020/4/29 17:08
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistrictFeature {

    private List<ProvinceAndCity> districts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProvinceAndCity{
        private String province;
        private String city;
    }
}
