package com.gx.oss;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/17 10:56
 */
//将数据源的自动配置排除调用，不排除报错
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = "com.gx") //重新配置扫描，可以将别的模块文件下，进行扫描得到
@EnableDiscoveryClient //开启服务发现
public class OssApplication8002 {
    public static void main(String[] args){
        SpringApplication.run(OssApplication8002.class, args);
    }
}
