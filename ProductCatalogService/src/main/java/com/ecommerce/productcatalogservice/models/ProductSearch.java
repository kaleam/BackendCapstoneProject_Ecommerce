package com.ecommerce.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Document(indexName = "products")
@Getter
@Setter
public class ProductSearch {
    @Field(type = FieldType.Long)
    private Long id;
    @Field(type = FieldType.Date)
    private Instant createdAt;
    @Field(type = FieldType.Date)
    private Instant updatedAt;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Double)
    private Double price;
    @Field(type = FieldType.Text)
    private String imageUrl;
    @Field(type = FieldType.Text)
    private String category;
}
