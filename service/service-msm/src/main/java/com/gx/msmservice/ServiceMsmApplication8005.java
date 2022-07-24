package com.gx.msmservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/22 16:51
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.gx")
public class ServiceMsmApplication8005 {
    public static void main(String[] args) {
        SpringApplication.run(ServiceMsmApplication8005.class, args);
    }
}
