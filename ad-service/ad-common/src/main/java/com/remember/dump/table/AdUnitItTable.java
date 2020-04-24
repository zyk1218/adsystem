package com.remember.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
  * @author remember
  * @date 2020/4/24 11:08
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUnitItTable {
    private Long unitId;
    private String itTag;
}
