package com.example.sbsj_process.product.review.service;

import com.example.sbsj_process.product.review.entity.ProductReview;
import com.example.sbsj_process.product.review.service.request.ReviewRegisterRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


public interface ReviewService {

    void reviewRegister(List<MultipartFile> imageFileList, ReviewRegisterRequest reviewRegisterRequest);

    List<ProductReview> list(Long productId);

    List<Map<String, Object>> starRateAverage(Long productId);

    void reviewDelete(Long reviewId);

    }
