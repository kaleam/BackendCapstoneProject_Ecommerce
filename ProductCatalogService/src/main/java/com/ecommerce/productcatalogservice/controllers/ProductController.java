package com.ecommerce.productcatalogservice.controllers;

import com.ecommerce.productcatalogservice.dtos.CategoryDto;
import com.ecommerce.productcatalogservice.dtos.ProductDto;
import com.ecommerce.productcatalogservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productcatalogservice.exceptions.ProductNotFoundException;
import com.ecommerce.productcatalogservice.models.Product;
import com.ecommerce.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    IProductService productService;

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<Product> productPage= productService.getAllProducts(page, size);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : productPage.getContent()) {
            productDtos.add(getProductDto(product));
        }
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) throws ProductNotFoundException {
        if (id <= 0)
            throw new IllegalArgumentException("product id is invalid");
        Product product = productService.getProductById(id);
        ProductDto productDto = getProductDto(product);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getProductByCategoryId(@RequestParam Long categoryId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) throws CategoryNotFoundException {
        if (categoryId <= 0)
            throw new IllegalArgumentException("category id is invalid");
        Page<Product> productPage = productService.getProductsByCategoryId(categoryId, page, size);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : productPage.getContent()) {
            productDtos.add(getProductDto(product));
        }
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    private ProductDto getProductDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory() != null){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setName(product.getCategory().getName());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }
}
