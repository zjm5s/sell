package com.imooc.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wochat")
public class WochatAccountConfig {
    private String mpAppId;
    private String mpAppSecret;
}
