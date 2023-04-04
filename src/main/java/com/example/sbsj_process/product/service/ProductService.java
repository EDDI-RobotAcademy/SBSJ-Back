package com.example.sbsj_process.product.service;

import com.example.sbsj_process.product.controller.form.ProductListResponse;
import com.example.sbsj_process.product.request.ProductRegisterRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    public void register(List<MultipartFile> imageFileList, ProductRegisterRequest productRegisterRequest);
    public List<ProductListResponse> getDefaultList();
}
