package com.ecommerce.productcatalogservice.services;

import com.ecommerce.productcatalogservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productcatalogservice.exceptions.ProductNotFoundException;
import com.ecommerce.productcatalogservice.models.Category;
import com.ecommerce.productcatalogservice.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    public Page<Product> getAllProducts(int page, int size);
    public Product getProductById(Long id) throws ProductNotFoundException;
    public Page<Product> getProductsByCategoryId(Long id, int page, int size) throws CategoryNotFoundException;
    public List<Product> searchProducts(String keyword);
    public Product createProduct(Product product);
}
