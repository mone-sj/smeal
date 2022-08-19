package com.campfire.smeal.config;

import com.campfire.smeal.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
//    @Bean
//    public BCryptPasswordEncoder encodePWD() {
//        System.out.println("encodePWD");
//        return new BCryptPasswordEncoder();
//    }

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                        .antMatchers("/","/auth/**", "/js/**","/css/**").permitAll()
                        .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/auth/login")
                    .loginProcessingUrl("/auth/loginProc")
                    .defaultSuccessUrl("/")
                .and()
                    .oauth2Login()
                .loginPage("/auth/login")
                .userInfoEndpoint()
                .userService(principalOauth2UserService)
        ;

        return http.build();
    }



}
