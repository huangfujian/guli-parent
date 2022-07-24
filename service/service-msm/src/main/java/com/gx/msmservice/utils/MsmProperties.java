package com.gx.msmservice.utils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/22 18:06
 */
@ConfigurationProperties(prefix = "ali.yun")
@Component
@Data
public class MsmProperties {
    private String accessKeyId;
    private String accessKeySecret;
    private String endpoint;
    private String signName;
    private String templateCode;
}
