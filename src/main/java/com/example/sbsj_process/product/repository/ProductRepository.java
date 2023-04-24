package com.example.sbsj_process.product.repository;

import com.example.sbsj_process.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository  extends JpaRepository<Product, Long> {
    @Query("Select p from Product p where p.productName = :productName")
    Optional<Product> findByProductName(String productName);

    Optional<Product> findByProductId(Long productId);

    List<Product> findByProductNameIn(List<String> query);

    List<Product> findByProductNameContaining(String name);
}
