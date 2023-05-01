package com.example.sbsj_process.product.review.service;

import com.example.sbsj_process.product.review.service.request.ReviewModifyRequest;
import com.example.sbsj_process.product.review.service.request.ReviewRegisterRequest;
import com.example.sbsj_process.product.review.service.response.MemberReviewListResponse;
import com.example.sbsj_process.product.review.service.response.ReviewListResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


public interface ReviewService {

    void reviewRegister(List<MultipartFile> imageFileList, ReviewRegisterRequest reviewRegisterRequest);

    void reviewWithImgRegister(List<MultipartFile> imageFileList, ReviewRegisterRequest reviewRegisterRequest);
    List<ReviewListResponse> list(Long productId, int startIndex, int endIndex);

    List<Map<String, Object>> starRateAverage(Long productId);

    void reviewDelete(Long productReviewId);

    void reviewModifyWithImage(List<MultipartFile> imageFileList, ReviewModifyRequest reviewModifyRequest);

    List<MemberReviewListResponse> getMemberReviewList(Long memberId);
}
