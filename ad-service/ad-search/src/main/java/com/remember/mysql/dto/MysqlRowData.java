package com.remember.mysql.dto;

import com.remember.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
  * @author remember
  * @date 2020/4/28 20:36
 * 用于构造增量数据，方便投递。
  */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MysqlRowData {
    private String tableName;
    private Integer level;
    private OpType opType;
    private List<Map<String,String>> fieldValueMap = new ArrayList<>();
}
