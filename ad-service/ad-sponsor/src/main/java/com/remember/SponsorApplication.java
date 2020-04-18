package com.remember;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
  * @author remember
  * @date 2020/4/18 18:22
 * sponsor程序入口
  */
@EnableFeignClients //该注解使得我们可以在该微服务里调用别的微服务，在投放系统中声明该注解是为了方便监控系统。
@EnableCircuitBreaker //断路器，也是为了实现监控。
@EnableEurekaClient
@SpringBootApplication
public class SponsorApplication {
    public static void main(String[] args) {
        SpringApplication.run(SponsorApplication.class,args);
    }
}
