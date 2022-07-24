package com.gx.serviceucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/22 20:11
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.gx")
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = "com.gx.serviceucenter.mapper")
public class ServiceUcApplication8160 {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUcApplication8160.class, args);
    }
}
