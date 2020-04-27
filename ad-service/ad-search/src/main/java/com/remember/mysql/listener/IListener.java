package com.remember.mysql.listener;

import com.remember.mysql.dto.BinlogEventRowData;

/**
  * @author remember
  * @date 2020/4/27 10:45
 * 对binlog增量的操作。
 * 定义成为接口是为了有更好的扩展性。
  */
public interface IListener {

    void register();//每个表都会对应不同的更新方法。 但是这个方法干啥用呢?

    void onEvent(BinlogEventRowData eventData);

}
