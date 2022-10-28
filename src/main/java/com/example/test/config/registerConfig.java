package com.example.test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "register-config")
public class registerConfig {
    private String accessKeyId;
    private String accessKeySecret;
    private String signName;
    private String setTemplateCode;
}
