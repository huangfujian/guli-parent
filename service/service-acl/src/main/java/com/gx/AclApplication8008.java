package com.gx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/28 19:52
 */
@MapperScan(basePackages = "com.gx.serviceacl.mapper")
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.gx")//指定扫描位置
public class AclApplication8008 {
    public static void main(String[] args) {
        SpringApplication.run(AclApplication8008.class, args);
    }
}
