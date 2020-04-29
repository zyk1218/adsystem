package com.remember.mysql.listener;

import com.remember.mysql.constant.Constant;
import com.remember.mysql.constant.OpType;
import com.remember.mysql.dto.BinlogEventRowData;
import com.remember.mysql.dto.MysqlRowData;
import com.remember.mysql.dto.TableTemplate;
import com.remember.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * @author remember
  * @date 2020/4/28 23:21
 * 对增量数据的监听，将Binlog对象转换成我们自己的对象。
  */
@Slf4j
@Component
public class IncrementListener implements IListener{

    @Resource(name = "")//为什么这里用空串指定？这样就可以让spring去按类型注入了。
    private ISender sender;

    private final AggregationListener aggregationListener;

    @Autowired
    public IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }

    @Override
    @PostConstruct
    public void register() {
        log.info("incrementListener register db and table info");
        Constant.table2Db.forEach((k,v)->{
            aggregationListener.register(v,k,this);
        });
    }
    @Override
    public void onEvent(BinlogEventRowData eventData) {
        //将event包装成mysqlRowData
        MysqlRowData mysqlRowData = new MysqlRowData();
        TableTemplate table = eventData.getTable();
        OpType type = OpType.to(eventData.getEventType());
        mysqlRowData.setTableName(table.getTableName());
        mysqlRowData.setLevel(table.getLevel());
        mysqlRowData.setOpType(type);
        //取出模板中对应的字段列表
        List<String> fieldValue = table.getOpTypeFieldSetMap().get(type);
        if(null == fieldValue){
            log.warn("{} not support for {}",type,table.getTableName());
            return;
        }
        //[2, 29, 测试插入]
        for(Map<String,String> afterMap : eventData.getAfter()){
            Map<String,String> _afterMap = new HashMap<>();
            for (Map.Entry<String, String> entry : afterMap.entrySet()) {
                _afterMap.put(entry.getKey(),entry.getValue());
            }
            mysqlRowData.getFieldValueMap().add(_afterMap);
        }
        //投递
        sender.sender(mysqlRowData);
    }
}
