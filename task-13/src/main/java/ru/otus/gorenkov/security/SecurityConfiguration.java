package ru.otus.gorenkov.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf().disable()
                .authorizeRequests(auth -> auth
//                        .antMatchers("/**").authenticated())
                        .anyRequest().authenticated())
                .formLogin()
                .and()
                .anonymous().principal(new User("anonymous", "", Collections.emptySet()))
                .and()
//                .logout().logoutSuccessUrl("/")
//                .and()
                .build();

    }
}
