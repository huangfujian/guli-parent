package com.gx.serviceedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/14 20:51
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.gx") //重新配置一下扫描,凡是com.gx下都进行扫描进来
@EnableTransactionManagement //开启事务管理
@EnableDiscoveryClient //开启服务发现
@EnableFeignClients//开启远程服务调用
public class EduApplication8001 {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication8001.class, args);
    }
}
