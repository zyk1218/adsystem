package com.remember.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
  * @author remember
  * @date 2020/4/24 11:07
  */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitKeywordTable {
    private Long unitId;
    private String keyword;
}
