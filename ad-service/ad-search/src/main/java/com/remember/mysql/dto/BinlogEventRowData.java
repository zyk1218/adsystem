package com.remember.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;

import java.util.List;
import java.util.Map;

/**
  * @author remember
  * @date 2020/4/27 10:40
 * 将binlog转换成Java对象,基于Event
  */
public class BinlogEventRowData {
    private TableTemplate table;
    private EventType eventType;
    private List<Map<String,String>> before;
    private List<Map<String,String>> after;
 }
