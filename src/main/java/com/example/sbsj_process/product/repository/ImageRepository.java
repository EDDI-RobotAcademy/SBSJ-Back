package com.example.sbsj_process.product.repository;

import com.example.sbsj_process.product.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("Select i from Image i join fetch i.product p where p.productId = :id")
    Image findByProductId(Long id);
}
