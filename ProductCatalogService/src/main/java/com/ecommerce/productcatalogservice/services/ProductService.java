package com.ecommerce.productcatalogservice.services;

import com.ecommerce.productcatalogservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productcatalogservice.exceptions.ProductNotFoundException;
import com.ecommerce.productcatalogservice.models.Category;
import com.ecommerce.productcatalogservice.models.Product;
import com.ecommerce.productcatalogservice.repos.ICategoryRepository;
import com.ecommerce.productcatalogservice.repos.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{
    @Autowired
    IProductRepository productRepository;

    @Autowired
    ICategoryRepository categoryRepository;

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
}
