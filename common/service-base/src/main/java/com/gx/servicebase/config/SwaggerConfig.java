package com.gx.servicebase.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author FL
 * @version 1.0
 * @date 2022/6/14 21:48
 */
@Configuration //声明为配置类
@EnableSwagger2 //开启swagger2
public class SwaggerConfig {
    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
//                .paths(Predicates.not(PathSelectors.regex("/admin/.*"))) //指定路径不进行测试
                .paths(Predicates.not(PathSelectors.regex("/erroe.*")))
                .build();
    }

    //webApi基本信息
    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                //标题
                .title("网站-课程中心API文档")
                //描述
                .description("本文档描述了课程中心微服务接口定义")
                //版本号
                .version("1.0")
                //联系
                .contact(new Contact("Helen", "http://atguigu.com", "2306605374@qq.com"))
                .build();
    }
}
