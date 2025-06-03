package com.ecommerce.paymentservice.configs;

import com.ecommerce.paymentservice.controllers.AuthInterceptor;
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
                .addPathPatterns("/api/payment/**")
                .excludePathPatterns("/api/payment/api-docs/**")
                .excludePathPatterns("/api/payment/swagger-ui.html")
                .excludePathPatterns("/api/payment/swagger-ui/**");
    }
}
