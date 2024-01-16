package com.ricardo.desafioanotaai.repository;

import com.ricardo.desafioanotaai.domain.product.Products;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Products, String> {
}
