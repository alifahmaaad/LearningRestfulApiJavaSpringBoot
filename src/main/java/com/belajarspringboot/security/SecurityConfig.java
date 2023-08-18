package com.belajarspringboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.belajarspringboot.utils.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity  
public class SecurityConfig{
     @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
     @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
                 .csrf(csrf -> csrf.disable())
                 .authorizeHttpRequests((authz) -> authz
                 .requestMatchers("/api/user").hasRole("ADMIN")
                 .requestMatchers("/api/user/**").permitAll()
                 .anyRequest().authenticated()
                 )
                 .addFilter(jwtAuthenticationFilter());
                //  .httpBasic(withDefaults()); //kalau ingin mengunnakan basicAuth sbgai contoh pake usernamedan password ketika hit endpoint
        return http.build();
    }
}
