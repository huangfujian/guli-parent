package com.gx.serviceucenter.utils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * @author FL
 * @version 1.0
 * @date 2022/6/23 22:41
 */
@ConfigurationProperties(prefix = "wx.open")
@Component
@Data
public class WxOpenProperties {
    private String appId;
    private String appSecret;
    private String redirectUrl;
}
