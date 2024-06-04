package com.moviezone.dai_api.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.moviezone.dai_api.config.JWTAuthFilter;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(
                        (authz) -> authz.anyRequest().authenticated())
                .addFilterBefore(jwtAuth(), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public JWTAuthFilter jwtAuth(){
        return new JWTAuthFilter(secretKey());
    }

    @Bean
    public SecretKey secretKey(){
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return secretKey;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web -> web.ignoring().requestMatchers("v1/auths", "v1/auths/**", "v1/health/**"));
    }
}
