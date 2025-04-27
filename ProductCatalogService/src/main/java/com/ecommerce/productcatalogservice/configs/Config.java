package com.ecommerce.productcatalogservice.configs;

import com.ecommerce.productcatalogservice.repos.IProductRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class Config {
}
