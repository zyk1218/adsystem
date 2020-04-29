package com.remember.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
  * @author remember
  * @date 2020/4/29 10:50
 * 对应着Binlog的一些配置。
  */
@Component
@ConfigurationProperties(prefix = "adconf.mysql")//该注解可以实现配置文件到对象的转换
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinlogConfig {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String binlogName;
    private Long position;
}
