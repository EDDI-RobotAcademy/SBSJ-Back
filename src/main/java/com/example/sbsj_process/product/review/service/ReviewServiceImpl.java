package com.example.sbsj_process.product.review.service;


import com.example.sbsj_process.account.entity.Member;
import com.example.sbsj_process.account.repository.MemberRepository;
import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.repository.ProductRepository;
import com.example.sbsj_process.product.review.entity.ProductReview;
import com.example.sbsj_process.product.review.entity.ReviewImage;
import com.example.sbsj_process.product.review.repository.ProductReviewRepository;
import com.example.sbsj_process.product.review.service.request.ReviewModifyRequest;
import com.example.sbsj_process.product.review.service.request.ReviewRegisterRequest;
import com.example.sbsj_process.product.review.service.response.ReviewListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


import com.example.sbsj_process.product.review.repository.ReviewImageRepository;

import javax.transaction.Transactional;

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

        log.info("받은 리뷰 정보: " + reviewRegisterRequest);

        if (reviewRegisterRequest == null || reviewRegisterRequest.getContext() == null
                || reviewRegisterRequest.getContext().isEmpty() || reviewRegisterRequest.getStarRate() == null) {
            throw new IllegalArgumentException("리뷰 등록 내용이 없습니다. " + reviewRegisterRequest);
        }

        List<ReviewImage> reviewImageList = new ArrayList<>();
        if (imageFileList != null && !imageFileList.isEmpty()) {
            final String fixedStringPath = "C:/lecture/SBSJ-Front/sbsj_web/src/assets/reviewImgs/";
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
                    System.out.println("에러 메시지: " + e.getMessage());

                }
            }

            for (String imageFileName : imageFileNameList) {
                ReviewImage reviewImage = new ReviewImage(imageFileName);
                reviewImageList.add(reviewImage);
            }
        }

        ProductReview productReview = new ProductReview();
        //하드코딩해제부분
        Long memberId = 3L; // 임시로 memberId에 3을 할당
        Long productId = 1L; // 임시로 productId에 1을 할당

        Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);
        Optional<Product> maybeProduct = productRepository.findByProductId(productId);

        try {
            if (maybeMember.isPresent() && maybeProduct.isPresent()) {
                productReview.setMember(maybeMember.get());
                productReview.setProduct(maybeProduct.get());
                productReview.setContext(reviewRegisterRequest.getContext());
                productReview.setStarRate(reviewRegisterRequest.getStarRate());
                productReview.setReviewImageList(reviewImageList);
            } else {
                throw new Exception("리뷰를 저장할 정보에 오류가 있습니다.");
            }
        } catch (Exception e) {
            System.out.println("리뷰 저장 중 오류가 발생했습니다.");
            System.out.println("에러 메시지: " + e.getMessage());
        }

        try {
            reviewRepository.save(productReview);
            if (!reviewImageList.isEmpty()) {
                reviewImageRepository.saveAll(reviewImageList);
            }
        } catch (Exception e) {
            log.error("리뷰 등록중 오류가 발생했습니다: " + e.getMessage());
            System.out.println("에러 메시지: " + e.getMessage());

        }

    }

    public void reviewWithImgRegister(List<MultipartFile> imageFileList, ReviewRegisterRequest reviewRegisterRequest) {
        if (reviewRegisterRequest == null || reviewRegisterRequest.getContext() == null
                || reviewRegisterRequest.getContext().isEmpty() || reviewRegisterRequest.getStarRate() == null) {
            throw new IllegalArgumentException("리뷰 등록 내용이 없습니다.");
        }

        log.info("리뷰 등록 요청을 받았습니다: " + reviewRegisterRequest);

        List<ReviewImage> reviewImageList = new ArrayList<>();
        if (imageFileList != null && !imageFileList.isEmpty()) {
            final String fixedStringPath = "../SBSJ-Front/sbsj_web/src/assets/reviewImgs/";

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

                } catch (IOException e) {
                    log.error("이미지 저장 중 에러 발생: " + e.getMessage());
                    log.error("저장 경로: " + fullPath);
                    throw new RuntimeException("리뷰이미지 등록실패했습니다.");
                }

                ReviewImage reviewImage = new ReviewImage(multipartFile.getOriginalFilename());
                reviewImageList.add(reviewImage);
            }
        }

        ProductReview productReview = new ProductReview();
        //하드코딩해제부분
        Long memberId = 3L; // 임시로 memberId에 3을 할당
        Long productId = 1L; // 임시로 productId에 1을 할당

        Optional<Member> maybeMember = memberRepository.findByMemberId(memberId);
        Optional<Product> maybeProduct = productRepository.findByProductId(productId);

        try {
            if (maybeMember.isPresent() && maybeProduct.isPresent()) {
                productReview.setMember(maybeMember.get());
                productReview.setProduct(maybeProduct.get());
                productReview.setContext(reviewRegisterRequest.getContext());
                productReview.setStarRate(reviewRegisterRequest.getStarRate());
                productReview.setReviewImageList(reviewImageList);

                for (ReviewImage reviewImage : reviewImageList) {
                    reviewImage.setProduct(maybeProduct.get());
                    reviewImage.setProductReview(productReview);
                }

            } else {
                throw new Exception("리뷰를 저장할 정보에 오류가 있습니다.");
            }
        } catch (Exception e) {
            System.out.println("리뷰 저장 중 오류가 발생했습니다.");
            System.out.println("에러 메시지: " + e.getMessage());
        }

        try {
            productReview.setReviewImageList(reviewImageList);
            ProductReview savedProductReview = reviewRepository.save(productReview);

            if (!reviewImageList.isEmpty()) {
                for (ReviewImage reviewImage : reviewImageList) {
                    reviewImage.setProductReview(savedProductReview);
                }
                reviewImageRepository.saveAll(reviewImageList);
            }
        } catch (Exception e) {
            log.error("리뷰 등록중 오류가 발생했습니다: " + e.getMessage());
            throw new RuntimeException("리뷰 최종등록실패!.");
        }

    }
    @Transactional
    public void reviewModifyWithImage(List<MultipartFile> imageFileList ,ReviewModifyRequest reviewModifyRequest) {
        if (reviewModifyRequest == null || reviewModifyRequest.getProductReviewId() == null) {
            throw new IllegalArgumentException("리뷰 수정 요청이 잘못되었습니다.");
        }

        Long productReviewId = reviewModifyRequest.getProductReviewId();
        log.info("리뷰 수정 요청을 받았습니다: " + reviewModifyRequest);

        Optional<ProductReview> maybeProductReview = reviewRepository.findByProductReviewId(productReviewId);

        if (!maybeProductReview.isPresent()) {
            throw new IllegalArgumentException("리뷰를 찾을 수 없습니다.");
        }

        ProductReview productReview = maybeProductReview.get();

        if (reviewModifyRequest.getContext() != null && !reviewModifyRequest.getContext().isEmpty()) {
            productReview.setContext(reviewModifyRequest.getContext());
        }

        if (reviewModifyRequest.getStarRate() != null) {
            productReview.setStarRate(reviewModifyRequest.getStarRate());
        }

        if (imageFileList != null && !imageFileList.isEmpty()) {
            final String fixedStringPath = "../SBSJ-Front/sbsj_web/src/assets/reviewImgs/";

            List<ReviewImage> reviewImageList = new ArrayList<>();
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

                } catch (IOException e) {
                    log.error("이미지 저장 중 에러 발생: " + e.getMessage());
                    log.error("저장 경로: " + fullPath);
                    throw new RuntimeException("리뷰이미지 등록실패했습니다.");
                }

                ReviewImage reviewImage = new ReviewImage(multipartFile.getOriginalFilename());
                reviewImage.setProduct(productReview.getProduct());
                reviewImage.setProductReview(productReview);
                reviewImageList.add(reviewImage);
            }

            if (productReview.getReviewImageList() != null && !productReview.getReviewImageList().isEmpty()) {
                reviewImageRepository.deleteAll(productReview.getReviewImageList());
            }
            productReview.setReviewImageList(reviewImageList);
            reviewImageRepository.saveAll(reviewImageList);
        }

        try {
            reviewRepository.save(productReview);
        } catch (Exception e) {
            log.error("리뷰 수정중 오류가 발생했습니다: " + e.getMessage());
            throw new RuntimeException("리뷰 수정 실패!.");
        }
    }

    //리뷰삭제 메서드
    @Override
    @Transactional
    public void reviewDelete(Long productReviewId) {
        Optional<ProductReview> maybeProductReview = reviewRepository.findByProductReviewId(productReviewId);

        if (maybeProductReview.isPresent()) {
            ProductReview review = maybeProductReview.get();

            // ReviewImage를 먼저 삭제
            if (review.getReviewImageList() != null && !review.getReviewImageList().isEmpty()) {
                reviewImageRepository.deleteAll(review.getReviewImageList());
            }

            // 이후 ProductReview를 삭제
            reviewRepository.delete(review);
        } else {
            throw new RuntimeException("존재하지 않는 리뷰입니다.");
        }
    }
    // 리뷰 리스트 반환

    @Transactional
    public List<ReviewListResponse> list(Long productId) {
        // 임시 상품 ID 값 설정
        productId = 1L;

        List<ProductReview> productReviews = reviewRepository.findByProduct_ProductId(productId);
        List<ReviewListResponse> reviewListResponses = new ArrayList<>();

        for (ProductReview productReview : productReviews) {
            ReviewListResponse reviewListResponse = new ReviewListResponse(productReview);
            reviewListResponses.add(reviewListResponse);
            System.out.println("리뷰 정보: " + reviewListResponse);
        }
        return reviewListResponses;
    }


    // 별점의 총합을 반환
    public List<Map<String, Object>> starRateAverage(Long productId) {
        List<Map<String, Object>> reviewAverages = new ArrayList<>();

        // 임시 상품 ID 값 설정
        productId = 1L;

        // 상품 ID에 해당하는 리뷰 목록 조회
        List<ProductReview> productreviews = reviewRepository.findByProduct_ProductId(productId);

        if (!productreviews.isEmpty()) {
            // 전체 리뷰 수와 전체 평점 합계 구하기
            Integer totalReviews = productreviews.size();
            int totalStarRates = 0;
            for (ProductReview productReview : productreviews) {
                totalStarRates += productReview.getStarRate();
            }

            // 평균 평점을 소수 첫째 자리까지 계산하고, Map에 넣은 후 반환 목록에 추가
            Double averageStarRate = Double.valueOf((double) totalStarRates / totalReviews);
            averageStarRate = Double.valueOf(Math.round(averageStarRate * 10) / 10.0);
            Map<String, Object> averageMap = new HashMap<>();
            averageMap.put("productId", productId);
            averageMap.put("totalReviews", totalReviews);
            averageMap.put("averageStarRate", averageStarRate);
            reviewAverages.add(averageMap);
        }

        // 콘솔에 정보 출력
        System.out.println("productId: " + productId);
        System.out.println("reviewAverages: " + reviewAverages);

        return reviewAverages;
    }

}