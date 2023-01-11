package com.example.test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config")
@Data
public class envConfig {
    private String env;
    private String imgPath;
    private String recipeImage;
    private String apkPath;
    private String apkBaseUrl;
}
