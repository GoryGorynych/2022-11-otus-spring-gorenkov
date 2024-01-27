package ru.otus.gorenkov.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests(auth -> auth
                        .antMatchers(HttpMethod.PUT, "/api/v1/books/*").hasRole(ADMIN_ROLE)
                        .antMatchers(HttpMethod.POST, "/api/v1/books/").hasRole(ADMIN_ROLE)
                        .antMatchers(HttpMethod.DELETE, "/api/v1/books/*").hasRole(ADMIN_ROLE)
                        .antMatchers("/edit/*").hasRole(ADMIN_ROLE)
                        .antMatchers(HttpMethod.GET, "/api/v1/**").hasAnyRole(ADMIN_ROLE, USER_ROLE)
                        .antMatchers("/comments").hasAnyRole(ADMIN_ROLE, USER_ROLE)
                        .antMatchers(HttpMethod.DELETE, "/api/v1/comments/*").hasRole(ADMIN_ROLE)
                        .antMatchers(HttpMethod.POST, "/api/v1/comments").hasAnyRole(ADMIN_ROLE, USER_ROLE)
                        .antMatchers("/").hasAnyRole(ADMIN_ROLE, USER_ROLE)
                        .antMatchers("/js/**").authenticated()
                        .anyRequest().denyAll())
                .formLogin();

        return http.build();
    }
}
