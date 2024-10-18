package com.api_rest.items_db_II.domain.repository;

import com.api_rest.items_db_II.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product,String> {

    @Query("{ 'name': ?0 }")
    Optional<Product> getProductByName(String name);

    @Query("{'name': ?0}")
    List<Product> getProductsByName(String name);
}
