package project.week03.logging.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.week03.logging.filter.JwtAuthenticationFilter;
import project.week03.logging.service.impl.CustomAuthenticationProvider;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return customAuthenticationProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Collections.singletonList(customAuthenticationProvider));
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                // Authentication endpoints - public
                .requestMatchers("/api/auth/**").permitAll()
                
                // Film API - READ operations are public, WRITE operations require authentication
                .requestMatchers("GET", "/api/films").permitAll()
                .requestMatchers("GET", "/api/films/{id}").permitAll()
                .requestMatchers("GET", "/api/films/search").permitAll()
                .requestMatchers("POST", "/api/films").authenticated()
                .requestMatchers("PUT", "/api/films/{id}").authenticated()
                .requestMatchers("DELETE", "/api/films/{id}").authenticated()
                
                // Language API - READ operations are public, WRITE operations require authentication
                .requestMatchers("GET", "/api/languages").permitAll()
                .requestMatchers("GET", "/api/languages/{id}").permitAll()
                .requestMatchers("POST", "/api/languages").authenticated()
                .requestMatchers("DELETE", "/api/languages/{id}").authenticated()
                
                // All other requests require authentication
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
