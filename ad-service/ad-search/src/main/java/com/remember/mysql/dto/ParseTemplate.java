package com.remember.mysql.dto;

import com.remember.mysql.constant.OpType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
  * @author remember
  * @date 2020/4/25 19:34
 * 对Template进行解析
 * 最终目的是要将Template类解析成ParseTemplate类
 * 这个类的特点是：两字段，一个是数据库名称，一个是集合类，对应的是表名和表模板。
  */
@Data
public class ParseTemplate {
    private String database;
    private Map<String,TableTemplate> tableTemplateMap = new HashMap<>();

    public static ParseTemplate parse(Template _template) {
        ParseTemplate template = new ParseTemplate();
        template.setDatabase(_template.getDatabase());
        for(JsonTable table : _template.getTableList()){
            String tableName = table.getTableName();
            Integer level = table.getLevel();
            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setTableName(tableName);
            tableTemplate.setLevel(level);
            template.tableTemplateMap.put(tableName,tableTemplate);
            //遍历操作类型对应的列
            Map<OpType,List<String>> opTypeFieldSetMap = tableTemplate.getOpTypeFieldSetMap();
            for (JsonTable.Column column : table.getInsert()) {
                getAndCreateIfNeed(OpType.ADD, opTypeFieldSetMap, ArrayList::new).add(column.getColumn());
            }
            for (JsonTable.Column column : table.getUpdate()) {
                getAndCreateIfNeed(OpType.UPDATE, opTypeFieldSetMap, ArrayList::new).add(column.getColumn());
            }
            for (JsonTable.Column column : table.getDelete()) {
                getAndCreateIfNeed(OpType.DELETE, opTypeFieldSetMap, ArrayList::new).add(column.getColumn());
            }

        }
        return template;
    }

    /**
     * 从Map中按Key取值，如果Key不存在那么就会创建该Key并存入Map
     */
    private static <T,R> R getAndCreateIfNeed(T key, Map<T,R> map, Supplier<R> factory){
        return map.computeIfAbsent(key,r->factory.get());
    }
}
