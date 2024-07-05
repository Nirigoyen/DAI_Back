package com.moviezone.dai_api.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.moviezone.dai_api.config.CorsFilter;
import javax.crypto.SecretKey;
import java.nio.channels.Channel;


@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Autowired
    private CorsFilter corsFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((authz) ->
                        authz.requestMatchers("/v1/auths", "/v1/auths/**", "/v1/health/**", "v1/ratings").permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(corsFilter, ChannelProcessingFilter.class)
                .addFilterBefore(jwtAuth(), UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable());
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
        return (web -> web.ignoring().requestMatchers("v1/auths", "v1/auths/**", "v1/health"));
    }

}
