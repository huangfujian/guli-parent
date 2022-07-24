package com.gx.aliyunvod.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/20 21:20
 */
@ConfigurationProperties(prefix = "aliyun.vod")
@Component
@Data
public class VodProperties {
    private String accessKeyId;
    private String accessKeySecret;
}
