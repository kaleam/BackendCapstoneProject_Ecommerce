package com.ecommerce.productcatalogservice.configs;

import com.ecommerce.productcatalogservice.controllers.AuthInterceptor;
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
                .addPathPatterns("/api/product/**")
                .excludePathPatterns("/api/product/api-docs/**")
                .excludePathPatterns("/api/product/swagger-ui.html")
                .excludePathPatterns("/api/product/swagger-ui/**");
    }
}
