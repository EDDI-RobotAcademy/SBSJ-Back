package com.example.sbsj_process.product.repository;

import com.example.sbsj_process.product.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
    @Query("Select i from ProductInfo i join fetch i.product p where p.productId = :productId")
    ProductInfo findByProductId(Long productId);
}
