package com.remember;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
  * @author remember
  * @date 2020/4/21 10:26
  */
@EnableFeignClients //使用feign去访问其他微服务
@EnableEurekaClient //作为Eureka
@EnableHystrix //断路器
@EnableCircuitBreaker //断路器
@EnableDiscoveryClient //微服务的发现能力
@EnableHystrixDashboard //Hystrix的监控
@SpringBootApplication
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class,args);
    }

    @Bean//RestTemplate是Spring提供的用于访问Rest服务的客户端。
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
