package com.example.sbsj_process.category.repository;

import com.example.sbsj_process.category.entity.Category;
import com.example.sbsj_process.category.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    @Query("select po from ProductOption po join fetch po.category c join fetch po.product where po.category.categoryId = :categoryId")
    List<ProductOption> findProductOptionListWithCategoryId(Long categoryId);
    @Query("select po from ProductOption po join fetch po.category c join fetch po.product where po.product.productId = :productId")
    List<ProductOption> findProductOptionListWithProductId(Long productId);
}
