package com.example.sbsj_process.product.service;

import com.example.sbsj_process.product.controller.form.ProductReadResponse;
import com.example.sbsj_process.product.service.request.ProductRegisterRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    public void register(List<MultipartFile> imageFileList, ProductRegisterRequest productRegisterRequest);
    ProductReadResponse read(Long productId);
    public List<String> getCategories();
}
