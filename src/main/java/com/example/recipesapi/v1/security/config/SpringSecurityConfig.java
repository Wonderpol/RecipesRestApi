package com.example.recipesapi.v1.security.config;

import com.example.recipesapi.v1.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final UserService userService;
    private final PasswordEncoderConfiguration passwordEncoderConfiguration;

    public SpringSecurityConfig(final UserService userService, final PasswordEncoderConfiguration passwordEncoderConfiguration) {
        this.userService = userService;
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/auth/register").permitAll()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-doc/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf().disable().headers().frameOptions().disable();

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoderConfiguration.encoder());
    }

}
