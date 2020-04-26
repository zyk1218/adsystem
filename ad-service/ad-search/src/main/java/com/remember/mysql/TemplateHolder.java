package com.remember.mysql;

import com.alibaba.fastjson.JSON;
import com.remember.mysql.constant.OpType;
import com.remember.mysql.dto.ParseTemplate;
import com.remember.mysql.dto.TableTemplate;
import com.remember.mysql.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
  * @author remember
  * @date 2020/4/26 10:58
 * 这个类完成了两个功能，一是将json文件加载出来，二是将原始索引与Binlog的索引对应起来。
  */
@Slf4j
@Component
public class TemplateHolder {
    private ParseTemplate template;
    private final JdbcTemplate jdbcTemplate;

    private String SQL_SCHEMA = "select table_schema,table_name,column_name,ordinal_position from information_schema.columns where table_schema = ? and table_name = ? ";

    @Autowired
    public TemplateHolder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void init(){
        loadJson("template.json");
    }

    public TableTemplate getTable(String tableName){
        return template.getTableTemplateMap().get(tableName);
    }
    private void loadJson(String path){
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream inStream = cl.getResourceAsStream(path);
        try{
            Template template = JSON.parseObject(inStream, Charset.defaultCharset(),Template.class);
            this.template = ParseTemplate.parse(template);
            loadMeta();
        }catch (IOException e){
            log.error(e.getMessage());
            throw new RuntimeException("fail to load json file .");
        }
    }

    private void loadMeta(){
        for(Map.Entry<String,TableTemplate> entry : template.getTableTemplateMap().entrySet()){
            TableTemplate table = entry.getValue();
            List<String> updateFields = table.getOpTypeFieldSetMap().get(OpType.UPDATE);
            List<String> insertFields = table.getOpTypeFieldSetMap().get(OpType.ADD);
            List<String> deleteFields = table.getOpTypeFieldSetMap().get(OpType.DELETE);
            jdbcTemplate.query(SQL_SCHEMA,new Object[]{template.getDatabase(),table.getTableName()},(rs,i) -> { //re->result Set,对应i
                int pos = rs.getInt("ordinal_position");
                String columnName = rs.getString("column_name");
                if(null != updateFields && updateFields.contains(columnName)
                        ||null != insertFields && insertFields.contains(columnName)
                        ||null != deleteFields && deleteFields.contains(columnName)){
                    table.getPosMap().put(pos - 1,columnName);
                }
                return null;
            });

        }
    }


}
