package com.remember.handler;

import com.remember.index.IndexAware;
import com.remember.mysql.constant.OpType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
  * @author remember
  * @date 2020/4/24 12:30
 * 索引处理
 * 索引之间有层级划分（依赖关系划分） 第一层是用户，第二层是推广计划及创意（这俩与其他层级都没关联）
 * 加载全量索引其实是增量索引“添加”的一种特殊实现
  */
@Slf4j
public class AdLevelDataHandler {
    private static <K,V> void handleBinlogEvent(IndexAware<K,V> index, K key, V value, OpType type){
        switch (type){
            case ADD:
                index.add(key,value);
                break;
            case DELETE:
                index.delete(key,value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            default:
                break;
        }
    }
}
