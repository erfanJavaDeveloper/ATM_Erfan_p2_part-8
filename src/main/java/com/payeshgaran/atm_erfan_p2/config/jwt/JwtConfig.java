package com.payeshgaran.atm_erfan_p2.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;


//@Configuration
@ConfigurationProperties("app.jwt")
@Data
public class JwtConfig {

    private String jwtSecret;
    private String jwtIssuer;
    private String tokenPrefix;



    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }


}
