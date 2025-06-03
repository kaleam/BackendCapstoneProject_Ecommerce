package com.ecommerce.ordermanagementservice.configs;

import com.ecommerce.ordermanagementservice.controllers.AuthInterceptor;
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
                .addPathPatterns("/api/order/**")
                .excludePathPatterns("/api/order/api-docs/**")
                .excludePathPatterns("/api/order/swagger-ui.html")
                .excludePathPatterns("/api/order/swagger-ui/**");
    }
}
