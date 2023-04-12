package com.example.sbsj_process.category.service;

import com.example.sbsj_process.category.controller.form.ProductListResponse;

import java.util.List;

public interface CategoryService {

    public void addCategory(String category);

    public List<ProductListResponse> getDefaultList();

    public List<ProductListResponse> getProductWithOption(String optionName);
}
