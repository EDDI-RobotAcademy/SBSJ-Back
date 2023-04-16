package com.example.sbsj_process.product.review.service;


import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.repository.MemberRepository;
import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.repository.ProductRepository;
import com.example.sbsj_process.product.review.entity.ProductReview;
import com.example.sbsj_process.product.review.entity.ReviewImage;
import com.example.sbsj_process.product.review.repository.ProductReviewRepository;
import com.example.sbsj_process.product.review.service.request.ReviewRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


import com.example.sbsj_process.product.review.repository.ReviewImageRepository;

@Service("reviewServiceImpl")
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    final private ProductReviewRepository reviewRepository;

    @Autowired
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
            final String fixedStringPath = "../lecture/reviewImgs";
            List<String> imageFileNameList = new ArrayList<>();

            for (MultipartFile multipartFile : imageFileList) {
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
                    throw new RuntimeException("리뷰이미지 등록실패했습니다.");

                }
            }

            for (String imageFileName : imageFileNameList) {
                ReviewImage reviewImage = new ReviewImage(imageFileName);
                reviewImageList.add(reviewImage);
            }
        }

        ProductReview productReview = new ProductReview();

        Long memberId = reviewRegisterRequest.getMemberId();
        Long productId = reviewRegisterRequest.getProductId();

        Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);
        Optional<Product> maybeProduct = productRepository.findByProductId(productId);

        if (maybeMember.isPresent() && maybeProduct.isPresent()) {
            productReview.setMember(maybeMember.get());
            productReview.setProduct(maybeProduct.get());
            productReview.setContext(reviewRegisterRequest.getContext());
            productReview.setStarRate(reviewRegisterRequest.getStarRate());
            productReview.setReviewImageList(reviewImageList);
        } else {
            throw new RuntimeException("리뷰를 저장할 정보에 오류가 있습니다.");
        }

        try {
            reviewRepository.save(productReview);
            if (!reviewImageList.isEmpty()) {
                reviewImageRepository.saveAll(reviewImageList);
            }
        } catch (Exception e) {
            log.error("리뷰 등록중 오류가 발생했습니다: " + e.getMessage());
            throw new RuntimeException("리뷰 최종등록실패!.");

        }

    }
    // 리뷰 리스트 반환

    public List<ProductReview> list(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("상품 ID에 오류가 있습니다.");
        }
        List<ProductReview> productReviews = reviewRepository.findByProduct_ProductId(productId);
        for (ProductReview productReview : productReviews) {
            List<ReviewImage> images = reviewImageRepository.findByProductReview_ReviewId(productReview.getReviewId());
            productReview.setReviewImageList(images);
        }
        return productReviews;
    }

    // 별점의 총합을 반환
    public List<Map<String, Object>> starRateAverage(Long productId) {
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

            // Calculate the average rating with one decimal place, put it in a Map, and add it to the return list
            double averageStarRate = (double) totalStarRates / totalReviews;
            averageStarRate = Math.round(averageStarRate * 10) / 10.0;
            Map<String, Object> averageMap = new HashMap<>();
            averageMap.put("productId", productId);
            averageMap.put("totalReviews", totalReviews);
            averageMap.put("averageStarRate", averageStarRate);
            reviewAverages.add(averageMap);
        }

        return reviewAverages;
    }
    //리뷰삭제 메서드
    @Override
    public void reviewDelete(Long reviewId) {
        Optional<ProductReview> maybeReview = reviewRepository.findByReviewId(reviewId);

        if (maybeReview.isPresent()) {
            ProductReview review = maybeReview.get();
            reviewRepository.delete(review);
        } else {
            throw new RuntimeException("존재하지 않는 리뷰입니다.");
        }
    }
//    @Override
//    public void modifyReview(Long reviewId, List<MultipartFile> imageFileList, ReviewRegisterRequest reviewRegisterRequest) {
//
//    }
//

//

}