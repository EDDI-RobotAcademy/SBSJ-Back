package com.example.sbsj_process.product.review.controller;

import com.example.sbsj_process.product.review.entity.ProductReview;
import com.example.sbsj_process.product.review.service.ReviewService;
import com.example.sbsj_process.product.review.service.request.ReviewRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    //하드코딩 풀 떄 mapping 수정
    @PostMapping(value = "/register")
    public void reviewRegister(@RequestBody ReviewRegisterRequest reviewRegisterRequest) {
        log.info("받은 리뷰 정보: " + reviewRegisterRequest);

        try {
            reviewService.reviewRegister(null, reviewRegisterRequest);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("요청 처리 중 에러가 발생했습니다.", e);
        }
    }

    //하드코딩 풀 떄 mapping 수정
    @PostMapping(value = "/registerWithImg", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    public void reviewRegisterWithImg(@RequestPart(value = "imageFileList", required = false) List<MultipartFile> imageFileList,
                                      @RequestPart(value = "reviewRegisterRequest") ReviewRegisterRequest reviewRegisterRequest) {
        log.info("reviewRegisterWithImg()");
        try {
            reviewService.reviewWithImgRegister(imageFileList, reviewRegisterRequest);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        return reviewService.list(productId);
    }

    @GetMapping(value = "/star-rate-average")
    public List<Map<String, Object>> getStarRateAverage(@RequestParam(value = "productId") Long productId) {
        return reviewService.starRateAverage(productId);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.reviewDelete(reviewId);
        return ResponseEntity.noContent().build();
    }
}