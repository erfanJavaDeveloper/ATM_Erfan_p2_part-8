package com.payeshgaran.atm_erfan_p2.config.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
@RequiredArgsConstructor
public class JwtSecretKey {
    private final  JwtConfig jwtConfig;

    @Bean
    public SecretKey secretKey(){
      return Keys.hmacShaKeyFor(jwtConfig.getJwtSecret().getBytes());
    }
}
