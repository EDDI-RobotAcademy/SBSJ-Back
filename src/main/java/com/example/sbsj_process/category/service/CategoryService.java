package com.example.sbsj_process.category.service;

import com.example.sbsj_process.category.service.response.ProductListResponse;

import java.util.List;

public interface CategoryService {

    public void addCategory(String category);

    public List<ProductListResponse> getDefaultList();

    public List<ProductListResponse> getDefaultPartialList(int startIndex, int endIndex);

    public List<ProductListResponse> getProductWithOption(String optionName);

    public List<ProductListResponse> getProductWithSearchQuery(List<String> query, int startIndex, int endIndex);

    public List<ProductListResponse> getProductSpecificList(String optionName, int startIndex, int endIndex);

    public List<ProductListResponse> getProductSpecificBrandList(String brand, int startIndex, int endIndex);
}
