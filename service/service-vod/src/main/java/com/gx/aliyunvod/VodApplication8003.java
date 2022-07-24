package com.gx.aliyunvod;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/20 19:10
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
//因为要把一些自定义配置给扫进来
@ComponentScan(basePackages = "com.gx")
public class VodApplication8003 {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication8003.class, args);
    }
}
