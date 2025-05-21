package com.ecommerce.productcatalogservice.services;

import com.ecommerce.productcatalogservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productcatalogservice.exceptions.ProductNotFoundException;
import com.ecommerce.productcatalogservice.models.Category;
import com.ecommerce.productcatalogservice.models.Product;
import com.ecommerce.productcatalogservice.models.ProductSearch;
import com.ecommerce.productcatalogservice.repos.jpa.ICategoryRepository;
import com.ecommerce.productcatalogservice.repos.jpa.IProductRepository;
import com.ecommerce.productcatalogservice.repos.elastic.IProductSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService implements IProductService{
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private IProductSearchRepository productSearchRepository;

    @Override
    public Page<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("name").ascending().and(Sort.by("price").descending()));
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product not found");
        }
        return productOptional.get();
    }

    @Override
    public Page<Product> getProductsByCategoryId(Long id, int page, int size) throws CategoryNotFoundException {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isEmpty()){
            throw new CategoryNotFoundException("Category not found");
        }
        Category category = categoryOptional.get();
        Pageable pageable = PageRequest.of(page,size, Sort.by("name").ascending().and(Sort.by("price").descending()));
        return productRepository.findAllByCategory(category, pageable);
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        // To integrate with ElasticSearch for full text search
        return List.of();
    }

    @Override
    public Product createProduct(Product product) {
        // check if category name is already present
        Optional<Category> categoryOptional = categoryRepository.findByName(product.getCategory().getName());
        categoryOptional.ifPresent(product::setCategory);
        Product savedProduct = productRepository.save(product);
        // save to elasticsearch
        productSearchRepository.save(mapToProductSearch(savedProduct));
        return savedProduct;
    }

    @Override public List<Product> search(String keyword) {
        Iterable<ProductSearch> productSearches = productSearchRepository.findByNameContainingOrCategoryContaining(keyword, keyword);
        return mapToProduct(productSearches);
    }

    private ProductSearch mapToProductSearch(Product product) {
        ProductSearch productSearch = new ProductSearch();
        productSearch.setId(product.getId());
        productSearch.setCreatedAt(product.getCreatedAt());
        productSearch.setUpdatedAt(product.getUpdatedAt());
        productSearch.setName(product.getName());
        productSearch.setDescription(product.getDescription());
        productSearch.setPrice(product.getPrice());
        productSearch.setImageUrl(product.getImageUrl());
        productSearch.setCategory(product.getCategory().getName());
        return productSearch;
    }

    private List<Product> mapToProduct(Iterable<ProductSearch> productSearches) {
        return StreamSupport.stream(productSearches.spliterator(), false)
                .map(productSearch -> {
                    Product product = new Product();
                    product.setId(productSearch.getId());
                    product.setCreatedAt(productSearch.getCreatedAt());
                    product.setUpdatedAt(productSearch.getUpdatedAt());
                    product.setName(productSearch.getName());
                    product.setDescription(productSearch.getDescription());
                    product.setPrice(productSearch.getPrice());
                    product.setImageUrl(productSearch.getImageUrl());
                    return product;
                })
                .collect(Collectors.toList());
    }
}
