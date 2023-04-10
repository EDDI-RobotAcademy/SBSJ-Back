package com.example.sbsj_process.category.controller;

import com.example.sbsj_process.category.controller.form.ProductListResponse;
import com.example.sbsj_process.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/category")
@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/default")
    public List<ProductListResponse> productDefaultList() {
        log.info("productDefaultList()");
        return categoryService.getDefaultList();
    }

    @GetMapping("/options/{optionName}")
    public List<ProductListResponse> getProductWithOptionList(@PathVariable("optionName") String optionName) {
        log.info("getProductWithOptionList()");
        return categoryService.getProductWithOptionList(optionName);
    }
}
