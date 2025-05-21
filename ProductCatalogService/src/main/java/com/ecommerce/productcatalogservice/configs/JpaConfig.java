package com.ecommerce.productcatalogservice.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.ecommerce.productcatalogservice.repos.jpa")
public class JpaConfig {
}
