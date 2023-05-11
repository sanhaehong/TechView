package com.sanhaehong.project.techview.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static com.sanhaehong.project.techview.domain.user.Role.USER;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final SecurityOAuth2UserService securityOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeHttpRequests()
                    .requestMatchers("/", "/css/**", "/img/**", "/js/**", "/login")
                    .permitAll()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .userInfoEndpoint()
                        .userService(securityOAuth2UserService);
        return http.build();
    }
}
