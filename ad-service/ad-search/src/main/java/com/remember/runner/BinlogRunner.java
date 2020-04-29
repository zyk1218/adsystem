package com.remember.runner;

import com.remember.mysql.BinlogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
  * @author remember
  * @date 2020/4/29 11:07
 * 用于控制程序启动后即开启对Binlog的监听。
 */
@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner{

    private final BinlogClient client;

    @Autowired
    public BinlogRunner(BinlogClient client) {
        this.client = client;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Coming in binlogRunner");
        client.connect();
    }
}
