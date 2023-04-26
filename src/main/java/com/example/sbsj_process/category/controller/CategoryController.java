package com.example.sbsj_process.category.controller;

import com.example.sbsj_process.category.service.response.ProductListResponse;
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
        log.info("getProductWithOption()");
        return categoryService.getProductWithOption(optionName);
    }

    @GetMapping("/{category}/{startIndex}/{endIndex}")
    public List<ProductListResponse> getProductSpecificPartialList(@PathVariable("category") String optionName,
                                                            @PathVariable("startIndex") int startIndex,
                                                            @PathVariable("endIndex") int endIndex) {
        log.info("getProductSpecificList()");
        if (optionName.equals("TOTAL")) {
            log.info("productDefaultPartialList()");
            return categoryService.getDefaultPartialList(startIndex, endIndex);
        }
        return categoryService.getProductSpecificList(optionName, startIndex, endIndex);
    }

    @GetMapping("/brand/{brand}/{startIndex}/{endIndex}")
    public List<ProductListResponse> getProductSpecificBrandPartialList(@PathVariable("brand") String brand,
                                                                        @PathVariable("startIndex") int startIndex,
                                                                        @PathVariable("endIndex") int endIndex) {
        log.info("getProductSpecificBrandPartialList");
        return categoryService.getProductSpecificBrandList(brand, startIndex, endIndex);
    }

    @GetMapping("/default/{startIndex}/{endIndex}")
    public List<ProductListResponse> productDefaultPartialList(@PathVariable("startIndex") int startIndex,
                                                                @PathVariable("endIndex") int endIndex) {
        log.info("productDefaultPartialList()");
        return categoryService.getDefaultPartialList(startIndex, endIndex);
    }

    @GetMapping("/search/{query}/{startIndex}/{endIndex}")
    public List<ProductListResponse> getProductWithSearchQuery(@PathVariable("query") List<String> query,
                                                               @PathVariable("startIndex") int startIndex,
                                                               @PathVariable("endIndex") int endIndex) {
        log.info("getProductWithSearchQuery(): " + query);
        return categoryService.getProductWithSearchQuery(query, startIndex, endIndex);
    }
}
