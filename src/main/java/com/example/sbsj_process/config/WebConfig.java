package com.example.sbsj_process.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins(
                "http://127.0.0.1:8080",
                "http://localhost:8080",
                "http://ec2-13-124-86-198.ap-northeast-2.compute.amazonaws.com"
        )
        .allowedMethods("GET", "POST", "PUT", "DELETE");

    }
}

