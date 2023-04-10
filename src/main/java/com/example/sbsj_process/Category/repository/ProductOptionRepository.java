package com.example.sbsj_process.category.repository;

import com.example.sbsj_process.category.entity.Category;
import com.example.sbsj_process.category.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    public List<ProductOption> findByCategoryIn(List<Category> categories);
}
