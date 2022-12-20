package com.payeshgaran.atm_erfan_p2.config.jwt;

import com.payeshgaran.atm_erfan_p2.security.UserManagement;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jWtTokenUtil;
    private final UserManagement userRepo;

//    public JwtTokenFilter(JwtTokenUtil jWtTokenUtil , UserManagement userRepo) {
//        this.jWtTokenUtil = jWtTokenUtil;
//        this.userRepo = userRepo;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || header.isEmpty() || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();
//        if (!jWtTokenUtil.validate(token)) {
        Jws<Claims> validate = jWtTokenUtil.validate(token);

        Claims body = validate.getBody();
        String username = body.getSubject();
        var authorities = (List<Map<String, String>>) body.get("authorities");
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = getAuthorities(authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                simpleGrantedAuthorities
        );

       SecurityContextHolder.getContext().setAuthentication(authentication);
       filterChain.doFilter(request,response);
    }

        private Set<SimpleGrantedAuthority> getAuthorities (List < Map < String, String >> authorities){
            return authorities.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());
        }


//        filterChain.doFilter(request, response);
//        return;
    }

//        UserDetails userDetails = userRepo
//                .loadUserByUsername(jWtTokenUtil.getUsername(token));
//
//    UsernamePasswordAuthenticationToken
//            authentication = new UsernamePasswordAuthenticationToken(
//            userDetails, null,
//            userDetails == null ?
//                    List.of() : userDetails.getAuthorities()
//    );
//
//        authentication.setDetails(
//                new
//
//    WebAuthenticationDetailsSource().
//
//    buildDetails(request)
//        );
//
//        SecurityContextHolder.getContext().
//
//    setAuthentication(authentication);
//        filterChain.doFilter(request,response);
//}


