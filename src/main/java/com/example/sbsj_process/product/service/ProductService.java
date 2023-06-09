package com.example.sbsj_process.product.service;

import com.example.sbsj_process.category.service.response.ProductListResponse;
import com.example.sbsj_process.product.service.request.ProductModifyRequest;
import com.example.sbsj_process.product.service.request.ProductRegisterRequestForTest;
import com.example.sbsj_process.product.service.response.ProductModifyFormResponse;
import com.example.sbsj_process.product.service.response.ProductReadResponse;
import com.example.sbsj_process.product.service.request.ProductRegisterRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    public void register(MultipartFile thumbnail, MultipartFile detail, ProductRegisterRequest productRegisterRequest);

    public void registerForTest(ProductRegisterRequestForTest productRegisterRequestForTest);

    public List<String> getCategories();

    public List<String> getBrands();

    public void addBrand(String brand);

    public void modify(Long productId , MultipartFile thumbnail, MultipartFile detail, ProductModifyRequest productModifyRequest);

    public ProductModifyFormResponse getModifyForm(Long productId);

    public void delete(Long productId);

    ProductReadResponse read(Long memberId, Long productId);

}
