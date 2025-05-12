package com.ecommerce.cartservice.repos;

import com.ecommerce.cartservice.models.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICartRepository extends MongoRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);
}
