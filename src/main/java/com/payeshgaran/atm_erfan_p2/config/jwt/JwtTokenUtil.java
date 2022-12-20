package com.payeshgaran.atm_erfan_p2.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payeshgaran.atm_erfan_p2.dto.person.PersonLogin;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static java.lang.String.format;

//@Component
@Slf4j
public class JwtTokenUtil extends UsernamePasswordAuthenticationFilter {

    private final JwtConfig jwtConfig;
    private AuthenticationManager authenticationManager;
    private final SecretKey secretKey;

    public JwtTokenUtil(JwtConfig jwtConfig, AuthenticationManager authenticationManager, SecretKey secretKey) {
        this.jwtConfig = jwtConfig;
        this.authenticationManager = authenticationManager;
        this.secretKey = secretKey;
        setFilterProcessesUrl("/Person/login");
    }

    public JwtTokenUtil(JwtConfig jwtConfig, SecretKey secretKey) {
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper mapper = new ObjectMapper();
        PersonLogin authenticationRequest = null;

        try {
            authenticationRequest = mapper.readValue(request.getInputStream(), PersonLogin.class);
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            return authenticate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + generateAccessToken(authResult));
    }

    public String generateAccessToken(Authentication authResult) {
        return Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuer(jwtConfig.getJwtIssuer())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 1000))  // 1 week
                .signWith(secretKey)
                .compact();
//                .compact();
//                .setExpiration(ChronoUnit.WEEKS.addTo(LocalDateTime.now(), 1L)) // 1 week
//                .setExpiration(new DateTime() ) // 1 week
//                .signWith(Keys.hmacShaKeyFor(jwtConfig.getJwtSecret().getBytes()))
//                .compact();

//        .signWith(SignatureAlgorithm.HS512, jwtConfig.getJwtSecret())
//                .compact();
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject().split(",")[0];
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject().split(",")[1];
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }

    public Jws<Claims> validate(String token) {
        try {
//            Jwts.parser().setSigningKey(jwtConfig.getJwtSecret()).parseClaimsJws(token);
          return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build().parseClaimsJws(token);
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
            throw new RuntimeException(ex);
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
            throw new RuntimeException(ex);
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
            throw new RuntimeException(ex);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
            throw new RuntimeException(ex);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

}
