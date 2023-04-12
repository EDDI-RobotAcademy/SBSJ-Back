package com.example.sbsj_process.product.review.service;


import com.example.sbsj_process.product.review.entity.ProductReview;
import com.example.sbsj_process.product.review.entity.ReviewImage;
import com.example.sbsj_process.product.review.repository.ProductReviewRepository;
import com.example.sbsj_process.product.review.service.request.ReviewRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.example.sbsj_process.product.review.repository.ReviewImageRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    final private ProductReviewRepository reviewRepository;

    final private ReviewImageRepository reviewImageRepository;

    @Override
    public void reviewRegister(List<MultipartFile> imageFileList, ReviewRegisterRequest reviewRegisterRequest) {
        if (reviewRegisterRequest == null || reviewRegisterRequest.getContext() == null
                || reviewRegisterRequest.getContext().isEmpty() || reviewRegisterRequest.getStarRate() == null) {
            throw new IllegalArgumentException("리뷰 등록 내용이 없습니다.");
        }

        log.info("리뷰 등록 요청을 받았습니다: " + reviewRegisterRequest);

        List<ReviewImage> reviewImageList = new ArrayList<>();
        if (imageFileList != null && !imageFileList.isEmpty()) {
            final String fixedStringPath = "../../SBSJ-Front/src/assets/reviewImgs/";
            List<String> imageFileNameList = new ArrayList<>();

            for (MultipartFile multipartFile: imageFileList) {
                log.info("파일 업로드 요청을 받았습니다 - 파일 이름: " + multipartFile.getOriginalFilename());
                if (multipartFile.isEmpty()) {
                    throw new IllegalArgumentException("업로드된 파일이 비어 있을 수 없습니다.");
                }

                String fullPath = fixedStringPath + multipartFile.getOriginalFilename();
                try {
                    FileOutputStream writer = new FileOutputStream(fullPath);
                    writer.write(multipartFile.getBytes());
                    writer.close();

                    imageFileNameList.add(multipartFile.getOriginalFilename());
                } catch (IOException e) {
                    log.error("리뷰등록 중 오류가 발생했습니다: " + e.getMessage());
                    // 적절한 방법으로 오류 처리를 수행합니다. 예를 들어, 사용자에게 오류 메시지를 반환합니다.
                }
            }

            for (String imageFileName : imageFileNameList) {
                ReviewImage reviewImage = new ReviewImage(imageFileName);
                reviewImageList.add(reviewImage);
            }
        }

        ProductReview productReview = new ProductReview();
        productReview.setContext(reviewRegisterRequest.getContext());
        productReview.setStarRate(reviewRegisterRequest.getStarRate());
        productReview.setReviewImageList(reviewImageList);


        try {
            reviewRepository.save(productReview);
            if (!reviewImageList.isEmpty()) {
                reviewImageRepository.saveAll(reviewImageList);
            }
        } catch (Exception e) {
            log.error("리뷰 등록중 오류가 발생했습니다: " + e.getMessage());
            // 적절한 방법으로 오류 처리를 수행합니다. 예를 들어, 사용자에게 오류 메시지를 반환합니다.
        }
    }
    // 리뷰 리스트 반환

    public List<ProductReview> list(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("상품 ID에 오류가 있습니다.");
        }
        List<ProductReview> productReviews = reviewRepository.findByProduct_ProductId(productId);
        for (ProductReview productReview : productReviews) {
            List<ReviewImage> images = reviewImageRepository.findByProduct_ProductId(productId);
            productReview.setReviewImageList(images);
        }
        return productReviews;
    }

    // 별점의 총합을 반환
    public List<Map<String, Object>> StarRateAverage(Long productId) {
        List<Map<String, Object>> reviewAverages = new ArrayList<>();

        // Look up the review list corresponding to the product ID
        List<ProductReview> productreviews = reviewRepository.findByProduct_ProductId(productId);

        if (!productreviews.isEmpty()) {
            // Get the sum of the total number of reviews and the total rating
            int totalReviews = productreviews.size();
            int totalStarRates = 0;
            for (ProductReview productReview : productreviews) {
                totalStarRates += productReview.getStarRate();
            }

            // Calculate the average rating, put it in a Map, and add it to the return list
            double averageStarRate = (double) totalStarRates / totalReviews;
            Map<String, Object> averageMap = new HashMap<>();
            averageMap.put("productId", productId);
            averageMap.put("totalReviews", totalReviews);
            averageMap.put("averageStarRate", averageStarRate);
            reviewAverages.add(averageMap);
        }

        return reviewAverages;
    }
//    }
}
