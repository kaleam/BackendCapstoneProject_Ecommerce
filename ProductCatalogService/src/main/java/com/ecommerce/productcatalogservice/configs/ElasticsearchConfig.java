package com.ecommerce.productcatalogservice.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.ecommerce.productcatalogservice.repos.elastic")
public class ElasticsearchConfig {
}
