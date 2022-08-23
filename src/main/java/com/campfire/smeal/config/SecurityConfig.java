package com.campfire.smeal.config;

import com.campfire.smeal.config.oauth.PrincipalOauth2UserService;
import com.campfire.smeal.handler.CustomAuthFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;
    private final AuthenticationFailureHandler customFailureHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                        .antMatchers("/","/auth/**", "/js/**","/css/**","/img/**"
                        ,"/vendor/**","/scss/**", "/favicon.ico")
                            .permitAll()
                        .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/auth/login")
                    .loginProcessingUrl("/auth/loginProc")
                    .defaultSuccessUrl("/dashboard")
                    .failureUrl("/auth/login")
                .and()
                    .oauth2Login()
                .loginPage("/auth/oauth2Login") // 추후에 /auth/login 로 바꾸기
                .failureHandler(customFailureHandler)
                .defaultSuccessUrl("/dashboard") // 삭제하기
                .userInfoEndpoint()
                .userService(principalOauth2UserService)
        ;

        return http.build();
    }



}
