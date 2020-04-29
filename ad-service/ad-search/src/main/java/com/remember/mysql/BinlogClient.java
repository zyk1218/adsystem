package com.remember.mysql;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.netflix.discovery.converters.Auto;
import com.remember.mysql.listener.AggregationListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
  * @author remember
  * @date 2020/4/29 10:56
  */
@Slf4j
@Component
public class BinlogClient {
    private BinaryLogClient client;

    private final BinlogConfig config;
    private final AggregationListener listener;

    @Autowired
    public BinlogClient(BinlogConfig config, AggregationListener listener) {
        this.config = config;
        this.listener = listener;
    }

    /*
    开启监听
     */
    public void connect(){
        new Thread(() -> {
                    client = new BinaryLogClient(config.getHost(),config.getPort(),config.getUsername(),config.getPassword());
                    if(!StringUtils.isEmpty(config.getBinlogName()) && config.getPosition() != -1){
                            client.setBinlogFilename(config.getBinlogName());
                            client.setBinlogPosition(config.getPosition());
                        }
                        client.registerEventListener(listener);
                        try{
                            log.info("connecting to mysql start");
                            client.connect();
                            log.info("connected to mysql");
                        }catch (IOException e){
                            e.printStackTrace();
                    }
                }
        ).start();
    }

    /*
    关闭监听
     */
    public void close(){
        try {
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
