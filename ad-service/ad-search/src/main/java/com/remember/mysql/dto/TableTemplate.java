package com.remember.mysql.dto;

import com.remember.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * @author remember
  * @date 2020/4/25 19:25
 * 方便一些Table的操作。
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableTemplate {
    private String tableName;
    private Integer level;

    /**
     * 操作类型和相关的列
     */
    private Map<OpType,List<String>> opTypeFieldSetMap = new HashMap<>();
    /**
     * 在binlog中不会显示字段名称，而是类似1、2、3这样的索引，该字段就是为此而建，可以让我们根据索引获得字段名称。
     */
    private Map<Integer,String> posMap = new HashMap<>();
}
