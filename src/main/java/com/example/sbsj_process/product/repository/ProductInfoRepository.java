package com.example.sbsj_process.product.repository;

import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
    @Query("Select i from ProductInfo i join fetch i.product p join fetch i.brand b where p.productId = :productId")
    ProductInfo findByProductId(Long productId);

    Optional<ProductInfo> findByProduct_ProductId(Long productId);
    @Query("select pi from ProductInfo pi join fetch pi.brand b join fetch pi.product p where pi.brand.brandName = :brandName")
    List<ProductInfo> findByBrandName(String brandName);
}
