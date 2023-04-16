package com.example.sbsj_process.ReiviewTest;


import com.example.sbsj_process.product.review.controller.ReviewController;
import com.example.sbsj_process.product.review.entity.ProductReview;
import com.example.sbsj_process.product.review.entity.ReviewImage;
import com.example.sbsj_process.product.review.repository.ProductReviewRepository;
import com.example.sbsj_process.product.review.repository.ReviewImageRepository;
import com.example.sbsj_process.product.review.service.ReviewService;
import com.example.sbsj_process.product.review.service.ReviewServiceImpl;
import com.example.sbsj_process.product.review.service.request.ReviewRegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
public class ReviewControllerTest {


    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private ReviewImageRepository reviewImageRepository;

    @Test
    public void 리뷰_등록_테스트() throws Exception {
        File[] files = new File[2];
        files[0] = new File("C:/Users/user2/Desktop/reviewtestImg/testimage1.jpg");
        files[1] = new File("C:/Users/user2/Desktop/reviewtestImg/testimage2.jpg");

        List<MultipartFile> multipartFiles = new ArrayList<>();
        System.out.println(files.length);

        for (File file : files) {
            try (FileInputStream input = new FileInputStream(file)) {
                MultipartFile multiPartFile = new MockMultipartFile(file.getName(), input.readAllBytes());
                multipartFiles.add(multiPartFile);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.out.println("here");
            }
        }

        if (multipartFiles.size() > 0) {
            System.out.println("multipartFiles.get(0).toString(): " + multipartFiles.get(0).toString());
        }

        if (multipartFiles.size() > 1) {
            System.out.println("multipartFiles.get(1).toString(): " + multipartFiles.get(1).toString());
        }

        Long memberId = 1234L;
        Long productId = 1234L;
        String context = "리뷰 테스트";
        Double starRate = 4D;

        ReviewRegisterRequest request = new ReviewRegisterRequest(memberId, productId, context, starRate);
        reviewService.reviewRegister(multipartFiles, request);

        List<ProductReview> productReviews = productReviewRepository.findAll();
        Assertions.assertEquals(1, productReviews.size(), "리뷰가 정상적으로 저장되지 않았습니다.");

        ProductReview savedReview = productReviews.get(0);
        Assertions.assertEquals(request.getContext(), savedReview.getContext(), "리뷰 내용이 일치하지 않습니다.");
        Assertions.assertEquals(request.getStarRate(), savedReview.getStarRate(), "별점이 일치하지 않습니다.");

        List<ReviewImage> reviewImages = reviewImageRepository.findAll();
        Assertions.assertEquals(multipartFiles.size(), reviewImages.size(), "이미지가 정상적으로 저장되지 않았습니다.");
    }



//    @Test
//    void testReviewList() throws Exception {
//        Long productId = 1L;
//        List<ProductReview> reviews = new ArrayList<>();
//
//        ProductReview review = new ProductReview();
//        review.setMemberId(1L);
//        review.setProductId(productId);
//        review.setContext("테스트 리뷰입니다.");
//        review.setStarRate(5D);
//        reviews.add(review);
//
//        when(reviewService.list(productId)).thenReturn(reviews);
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/review/list")
//                        .param("productId", productId.toString())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].memberId").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productId").value(productId))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].context").value("테스트 리뷰입니다."))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].starRate").value(5D));
//    }

//    @Test
//    void testGetStarRateAverage() throws Exception {
//        Long productId = 1L;
//        List<Map<String, Object>> reviewAverages = new ArrayList<>();
//        Map<String, Object> averageMap = new HashMap<>();
//        averageMap.put("productId", productId);
//        averageMap.put("totalReviews", 1);
//        averageMap.put("averageStarRate", 5.0);
//        reviewAverages.add(averageMap);
//
//        when(reviewService.starRateAverage(productId)).thenReturn(reviewAverages);
//
//        ResultActions productId1 = this.mockMvc.perform(MockMvcRequestBuilders.get("/review/star-rate-average")
//                        .param("productId", productId.toString())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productId").value(productId))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalReviews").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].averageStarRate").value(5.0));
//    }
}
