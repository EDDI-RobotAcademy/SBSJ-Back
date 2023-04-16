package com.example.sbsj_process.product.service;

import com.example.sbsj_process.product.entity.Image;
import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.entity.ProductInfo;
import com.example.sbsj_process.product.entity.Wish;
import com.example.sbsj_process.product.repository.ImageRepository;
import com.example.sbsj_process.product.repository.ProductInfoRepository;
import com.example.sbsj_process.product.repository.ProductRepository;
import com.example.sbsj_process.product.repository.WishRepository;
import com.example.sbsj_process.product.service.response.WishListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishServiceImpl implements WishService {

    private final ProductRepository productRepository;
    private final ProductInfoRepository productInfoRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;
    private final WishRepository wishRepository;

    @Override
    public List<WishListResponse> getWishList(Long memberId) {
        List<Wish> wishList = wishRepository.findByMember_MemberId(memberId);

        if(wishList.size() == 0) {
            return null;
        }

        List<WishListResponse> wishListResponses = new ArrayList<>();
        for(Wish wish: wishList) {
            Long productId = wish.getProduct().getProductId();
            Optional<Product> maybeProduct = productRepository.findByProductId(productId);
            Optional<ProductInfo> maybeProductInfo = productInfoRepository.findByProduct_ProductId(productId);
            Optional<Image> maybeImage = imageRepository.findByProduct_ProductId(productId);

            WishListResponse wishListResponse = new WishListResponse(
                    maybeProduct.get(),
                    maybeProductInfo.get(),
                    maybeImage.get()
            );
            wishListResponses.add(wishListResponse);
        }

        return wishListResponses;
    }

}
