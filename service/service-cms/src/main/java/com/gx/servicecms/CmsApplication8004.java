package com.gx.servicecms;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/21 22:04
 */
@SpringBootApplication
@MapperScan("com.gx.servicecms.mapper")
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = "com.gx")//指定扫描位置
public class CmsApplication8004 {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication8004.class, args);
    }
}
