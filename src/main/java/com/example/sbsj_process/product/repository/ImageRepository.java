package com.example.sbsj_process.product.repository;

import com.example.sbsj_process.product.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("Select i from Image i join fetch i.product p where p.productId = :productId")
    Image findByProductId(Long productId);

    Optional<Image> findByProduct_ProductId(Long productId);
    @Modifying
    @Query("delete from Image i where i.product.productId = :productId")
    void deleteByProductId(@Param("productId") Long productId);
    @Query("select i from Image i join fetch i.product p where i.thumbnail = :thumbnail")
    List<Image> findByThumbnail(String thumbnail);

    @Query("select i from Image i join fetch i.product p where i.detail = :detail")
    List<Image> findByDetail(String detail);
}
