package com.ecommerce.productcatalogservice.repos.elastic;

import com.ecommerce.productcatalogservice.models.ProductSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductSearchRepository extends ElasticsearchRepository<ProductSearch, Long> {
    Iterable<ProductSearch> findByNameContainingOrCategoryContaining(String name, String category);
}
