package com.gx.serviceorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/25 11:13
 */
@SpringBootApplication
@MapperScan("com.gx.serviceorder.mapper")
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan("com.gx")
public class OrderApplication8006 {
    public static void main(String[] args) {

        SpringApplication.run(OrderApplication8006.class, args);
    }
}
