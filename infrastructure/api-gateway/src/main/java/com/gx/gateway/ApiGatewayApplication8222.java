package com.gx.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication8222 {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication8222.class, args);
    }
}
