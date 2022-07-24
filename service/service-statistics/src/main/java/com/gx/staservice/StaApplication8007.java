package com.gx.staservice;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/27 10:31
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.gx")
@EnableFeignClients
@MapperScan(basePackages = "com.gx.staservice.mapper")
@EnableScheduling //开启定时任务
public class StaApplication8007 {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication8007.class);
    }
}
