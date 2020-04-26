package com.remember.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

/**
  * @author remember
  * @date 2020/4/25 15:35
 * 测试
  */
public class BinlogServiceTest {
    public static void main(String[] args) throws Exception{
        //客户端连接，实质上是将我们本地（这个本地指的就是我们的127.0.0.1）MySQL伪装成一个Slave，绑定于Master
//        Update------------------
//        UpdateRowsEventData{tableId=102, includedColumnsBeforeUpdate={0, 1, 2}, includedColumns={0, 1, 2}, rows=[
//            {before=[1, 23, 测试], after=[1, 23, 更新测试]}
//]}
//        Write------------------
//        WriteRowsEventData{tableId=102, includedColumns={0, 1, 2}, rows=[
//    [2, 29, 测试插入]
//]}
//        Delete------------------
//        DeleteRowsEventData{tableId=102, includedColumns={0, 1, 2}, rows=[
//    [1, 23, 更新测试]
//]}
        BinaryLogClient client = new BinaryLogClient("127.0.0.1",3306,"root","1234");
        client.setBinlogFilename("mysql-bin.000001");
        client.registerEventListener(event -> {
            EventData data = event.getData();
            if(data instanceof UpdateRowsEventData){
                System.out.println("Update------------------");
                System.out.println(data.toString());
            }else if (data instanceof WriteRowsEventData){
                System.out.println("Write------------------");
                System.out.println(data.toString());
            }else if (data instanceof DeleteRowsEventData){
                System.out.println("Delete------------------");
                System.out.println(data.toString());
            }
        });
        client.connect();
    }
}
