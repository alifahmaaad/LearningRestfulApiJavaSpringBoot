package com.belajarspringboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.belajarspringboot.utils.JwtAuthenticationFilter;
import com.belajarspringboot.utils.JwtUtil;

@Configuration
@EnableWebSecurity  
public class SecurityConfig{
     @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
    @Bean
    JwtUtil jwtUtil() {
        return new JwtUtil();
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
     @Bean
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
                 .csrf(csrf -> csrf.disable())
                 .authorizeHttpRequests((authz) -> authz
                 .requestMatchers("/api/user").hasRole("ADMIN")
                 .requestMatchers("/api/user/**").permitAll()
                 .requestMatchers("/swagger-ui").permitAll()
                 .anyRequest().authenticated()
                 )
                 .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
                //  .httpBasic(withDefaults()); //kalau ingin mengunnakan basicAuth sbgai contoh pake usernamedan password ketika hit endpoint
        return http.build();
    }
}
