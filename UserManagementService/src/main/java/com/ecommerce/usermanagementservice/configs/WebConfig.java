package com.ecommerce.usermanagementservice.configs;

import com.ecommerce.usermanagementservice.controllers.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/user/**")
                .excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/user/signup")
                .excludePathPatterns("/api/user/api-docs/**")
                .excludePathPatterns("/api/user/swagger-ui.html")
                .excludePathPatterns("/api/user/swagger-ui/**");
    }
}