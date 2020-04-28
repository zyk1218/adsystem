package com.remember.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.remember.mysql.TemplateHolder;
import com.remember.mysql.dto.BinlogEventRowData;
import com.remember.mysql.dto.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
  * @author remember
  * @date 2020/4/27 11:04
 * 收集监听到的信息，因为要收集信息，所以需要实现BinaryLogClient下的EventListener接口，并实现onEvent方法。
 * 具体的业务是将Event里的Data转变成RowData
  */
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener{
    private String dbName;
    private String tableName;
    private Map<String,IListener> listenerMap = new HashMap<>();
    private final TemplateHolder templateHolder;

    @Autowired
    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    //该方法是本类的核心。
    @Override
    public void onEvent(Event event) {
        EventType type = event.getHeader().getEventType();
        log.debug("event type : {}",type);
        if(type == EventType.TABLE_MAP){
            TableMapEventData data = event.getData();
            this.tableName = data.getTable();
            this.dbName = data.getDatabase();
            return;
        }
        if(type != EventType.EXT_UPDATE_ROWS && type != EventType.EXT_DELETE_ROWS && type != EventType.EXT_WRITE_ROWS){
            return;
        }
        //数据库和数据表名是否已被填充
        if(StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)){
            log.error("no meta data event");
        }
        //获取对应的监听器
        String key = genKey(dbName,tableName);
        IListener listener = listenerMap.get(key);
        //如果listener不存在就说明我们业务对当前发生变化的表并不感兴趣
        if(listener == null){
            log.debug("skip {}",key);
            return;
        }
        log.info("trigger event : {}",type.name());
        try{
            BinlogEventRowData rowData = buildRowData(event.getData());
            if(rowData == null){
                return;
            }
            rowData.setEventType(type);
            listener.onEvent(rowData);

        }catch (Exception ex){
            ex.printStackTrace();
            log.error(ex.getMessage());
        }finally {
            this.dbName = "";
            this.tableName = "";
        }

    }

    public void register(String dbName,String tableName,IListener listener){
        log.info("register : {}-{}",dbName,tableName);
        this.listenerMap.put(genKey(dbName,tableName),listener);
    }

    private BinlogEventRowData buildRowData(EventData eventData){
        TableTemplate table = templateHolder.getTable(tableName);
        if(table == null){
            log.warn("table {} not found",tableName);
            return null;
        }
        //Event的Rows转换成List<Map<列名，列名对应的值>>
        List<Map<String,String>> afterMapList = new ArrayList<>();
        for (Serializable[] after : getAfterValues(eventData)) {
            Map<String,String> afterMap = new HashMap<>();
            int colLen = after.length;
            for(int ix = 0 ; ix < colLen ; ++ix){
                //取出当前位置对应的列名
                String colName = table.getPosMap().get(ix);
                //如果列名不存在，说明我们并不关心这个列
                if(colName == null){
                    log.debug("ignore position : {}", ix);
                    continue;
                }
                String colValue = after[ix].toString();
                afterMap.put(colName,colValue);
            }
            afterMapList.add(afterMap);
        }
        BinlogEventRowData rowData = new BinlogEventRowData();
        rowData.setAfter(afterMapList);
        rowData.setTable(table);
        return rowData;
    }

    private List<Serializable[]> getAfterValues(EventData eventData){
        if(eventData instanceof WriteRowsEventData){
            return ((WriteRowsEventData)eventData).getRows();
        }
        if(eventData instanceof UpdateRowsEventData){
            return ((UpdateRowsEventData)eventData).getRows().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        }
        if(eventData instanceof DeleteRowsEventData){
            return ((DeleteRowsEventData)eventData).getRows();
        }
        return Collections.emptyList();
    }

    private String genKey(String dbName,String tableName){
        return dbName + ":" + tableName;
    }
}
