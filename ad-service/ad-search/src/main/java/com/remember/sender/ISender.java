package com.remember.sender;

import com.remember.mysql.dto.MysqlRowData;

/**
  * @author remember
  * @date 2020/4/28 23:14
 * 将增量数据投递给索引服务，使得索引服务可以构造增量索引
  */
public interface ISender {
    void sender(MysqlRowData rowData);
}
