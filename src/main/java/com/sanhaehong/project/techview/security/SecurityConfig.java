package com.sanhaehong.project.techview.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static com.sanhaehong.project.techview.domain.user.Role.ADMIN;
import static com.sanhaehong.project.techview.domain.user.Role.USER;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final SecurityOAuth2UserService securityOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .requiresChannel(channel -> channel.anyRequest().requiresSecure())
                .csrf().disable()
                .cors().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeHttpRequests()
                    .requestMatchers("/", "/css/**", "/img/**", "/js/**", "/login", "/question/lists", "/question/view/**", "/mockexam")
                    .permitAll()
                    .requestMatchers("/question/view/*", "/question/*/answer/**", "/mockexam/**", "/mockexam/process/**", "/mockexam/history/**", "/mockexam/select/exam/*/ready")
                    .hasAnyRole(USER.name(), ADMIN.name())
                    .requestMatchers("/question/add")
                    .hasRole(ADMIN.name())
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
