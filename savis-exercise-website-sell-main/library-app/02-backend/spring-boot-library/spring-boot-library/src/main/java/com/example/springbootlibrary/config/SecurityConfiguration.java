package com.example.springbootlibrary.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{

//           http
//                   .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/api/books/secure/**").authenticated())
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        http.authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/books/secure/**",
                                "/api/reviews/secure/**",
                                "/api/messages/secure/**",
                                "/api/admin/secure/**")
                        .authenticated()
                        .anyRequest().permitAll())
                        .oauth2ResourceServer()
                        .jwt();

           http.cors();

        http.setSharedObject(ContentNegotiationStrategy.class,
                new HeaderContentNegotiationStrategy());

        Okta.configureResourceServer401ResponseBody(http);

        return http.build();
    }

}

