package com.example.sbsj_process.product.service;

import com.example.sbsj_process.product.dto.request.ProductRegisterRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    public void register(List<MultipartFile> imageFileList, ProductRegisterRequest productRegisterRequest);
}
