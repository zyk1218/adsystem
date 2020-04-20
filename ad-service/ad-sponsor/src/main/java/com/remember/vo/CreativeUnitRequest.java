package com.remember.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
  * @author remember
  * @date 2020/4/20 18:56
 * 推广单元与创意相联系
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeUnitRequest {

    private List<CreativeUnitItem> unitItems;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreativeUnitItem{
        private Long creativeId;
        private Long unitId;
    }
}
