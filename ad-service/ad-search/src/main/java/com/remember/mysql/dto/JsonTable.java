package com.remember.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
  * @author remember
  * @date 2020/4/25 19:14
 * 将JSON格式转化为对应的Java对象-》
  */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonTable {
    private String tableName;
    private Integer level;

    private List<Column> update;
    private List<Column> delete;
    private List<Column> insert;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Column{
        private String column;
    }
}
