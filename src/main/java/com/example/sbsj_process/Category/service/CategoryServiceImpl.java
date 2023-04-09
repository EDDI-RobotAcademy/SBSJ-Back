package com.example.sbsj_process.Category.service;

import com.example.sbsj_process.Category.entity.Category;
import com.example.sbsj_process.Category.repository.CategoryRepository;
import com.example.sbsj_process.Category.repository.ProductOptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductOptionRepository productOptionRepository;

    public void addCategory(String category) {
        Category productCategory = new Category(category);
        categoryRepository.save(productCategory);
    }
}
