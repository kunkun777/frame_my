package com.zxc.uitls;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;
import java.time.Duration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class TokenSetting {
    private String secretKey;
    private Duration accessTokenExpireTime;
    private Duration refreshTokenExpireTIme;
    private Duration refreshTokenExpireAppTIme;
    private String issuer;
}
