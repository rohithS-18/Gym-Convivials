package com.gym.convivials.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("cloudflare")
@Getter
@Setter
public class CloudflareProps {
    private String tokenValue;
    private String accessKeyID;
    private String secretAccessKey;
    private String endpoint;
    private String accountId;
    private String bucket;
}
