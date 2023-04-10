package com.example.sbsj_process.product.review.controller;

import com.example.sbsj_process.product.review.entity.ProductReview;
import com.example.sbsj_process.product.review.service.ReviewService;
import com.example.sbsj_process.product.review.service.request.ReviewRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> reviewRegister(@RequestParam(value = "imageFileList", required = false) List<MultipartFile> imageFileList,
                                            @RequestParam(value = "reviewRegisterRequest") ReviewRegisterRequest reviewRegisterRequest) {
        try {
            reviewService.reviewRegister(imageFileList, reviewRegisterRequest);
            return new ResponseEntity<>("Review registered successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/list")
    public ResponseEntity<?> reviewList(@RequestParam(value = "productId") Long productId) {
        try {
            List<ProductReview> reviewList = reviewService.list(productId);
            return new ResponseEntity<>(reviewList, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/star-rate-average")
    public ResponseEntity<?> getStarRateAverage(@RequestParam(value = "productId") Long productId) {
        try {
            List<Map<String, Object>> reviewAverages = reviewService.StarRateAverage(productId);
            return new ResponseEntity<>(reviewAverages, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}