package com.example.EmployeeManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfiguration() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200", "http://ec2-44-222-199-39.compute-1.amazonaws.com", "http://ec2-44-222-199-39.compute-1.amazonaws.com:80")
                        .allowedMethods("GET", "POST", "DELETE", "POST", "PUT")
                        .allowedMethods("GET", "POST", "DELETE", "POST", "PUT")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
