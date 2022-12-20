package com.payeshgaran.atm_erfan_p2.config;

import com.payeshgaran.atm_erfan_p2.config.jwt.JwtConfig;
import com.payeshgaran.atm_erfan_p2.config.jwt.JwtSecretKey;
import com.payeshgaran.atm_erfan_p2.config.jwt.JwtTokenFilter;
import com.payeshgaran.atm_erfan_p2.config.jwt.JwtTokenUtil;
import com.payeshgaran.atm_erfan_p2.security.UserManagement;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


//@EnableMethodSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Profile("test")
//    ******************************************

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserManagement userManagement;
//    private final JwtTokenUtil jwtTokenUtil;
    private final JwtConfig jwtConfig;
    private final JwtSecretKey secretKey;

//    public SecurityConfig(UserManagement userManagement , JwtConfig jwtConfig, JwtSecretKey secretKey) {
//        this.userManagement = userManagement;
//        this.jwtConfig = jwtConfig;
//        this.secretKey = secretKey;
//    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        System.out.println("done authentication ");
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil(jwtConfig, authenticationManager(), secretKey.secretKey());
//        http
//                .csrf().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilter(jwtTokenUtil)
////                .addFilterAfter(jwtTokenFilter,JwtTokenUtil.class)  //todo
//                .addFilterAfter(new JwtTokenFilter(jwtTokenUtil,userManagement),JwtTokenUtil.class)
////  //              .addFilter(jwtTokenFilter)//todo
//                .authorizeRequests()
//                .antMatchers("/login","/swagger-ui/index.html/**" ,"/swagger-ui/**", "/swagger-ui.html","/docs"  ).permitAll()
//                .antMatchers("/h2-console/**").permitAll()
////        //        .antMatchers("/**/**").hasAnyRole("ADMIN", "USER")//todo
//                .anyRequest()
//                .authenticated();
        //*********************************************************************
//  //              .and()
//  //              .httpBasic()
//                .and()
//                .formLogin()
//                .permitAll();

        http
                .csrf().disable()
//        csrf().disable(),
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtTokenUtil)
                .addFilterAfter(new JwtTokenFilter(jwtTokenUtil,userManagement),JwtTokenUtil.class)
                .authorizeRequests()
                .antMatchers("/Person", "/Person/sign-up",  "/users/login" ,//"/users/verify/**", "/users/login",
                        "/docs" ,"/h2-console/**",
//                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html/**",
                        "/users/register").permitAll()
                .anyRequest()
                .authenticated()
                        .and()
                .headers().frameOptions().disable();


    }


//    ******************************************

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userManagement);
        return provider;
    }


//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManager();
//    }

    //todo ******************************
    //todo inmemory userditailService
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails erfan = Person.builder()
//                .username("user")
//                .password(passwordEncoder().encode("123"))
//                .authorities(USER.getAuthority())
//                .build();
//
//        UserDetails admin = Person.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("123"))
//                .authorities(ADMIN.getAuthority())
//                .build();
//
//        return new InMemoryUserDetailsManager(erfan, admin);
//    }
    //    ******************************************

}
