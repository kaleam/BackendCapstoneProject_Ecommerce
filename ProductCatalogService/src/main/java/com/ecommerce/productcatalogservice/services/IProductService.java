package com.ecommerce.productcatalogservice.services;

import com.ecommerce.productcatalogservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productcatalogservice.exceptions.ProductNotFoundException;
import com.ecommerce.productcatalogservice.models.Category;
import com.ecommerce.productcatalogservice.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProductService {
    Page<Product> getAllProducts(int page, int size);
    Product getProductById(Long id) throws ProductNotFoundException;
    Page<Product> getProductsByCategoryId(Long id, int page, int size) throws CategoryNotFoundException;
    List<Product> searchProducts(String keyword);
    Product createProduct(Product product);
    List<Product> search(String keyword);
}
