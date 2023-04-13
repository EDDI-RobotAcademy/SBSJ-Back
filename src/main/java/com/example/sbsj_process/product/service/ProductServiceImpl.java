package com.example.sbsj_process.product.service;

import com.example.sbsj_process.category.entity.ProductOption;
import com.example.sbsj_process.category.repository.CategoryRepository;
import com.example.sbsj_process.category.repository.ProductOptionRepository;
import com.example.sbsj_process.category.controller.form.ProductListResponse;
import com.example.sbsj_process.product.controller.form.ProductReadResponse;
import com.example.sbsj_process.product.entity.*;
import com.example.sbsj_process.product.repository.*;
import com.example.sbsj_process.product.service.request.ProductRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductInfoRepository productInfoRepository;
    private final ImageRepository imageRepository;
    private final CategoryRepository categoryRepository;
    private final ProductOptionRepository productOptionRepository;



    public List<ProductListResponse> getDefaultList() {
        List<ProductListResponse> productListResponses = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        String title, thumbnail;
        Long productId, price, wish;

        for(int i = 0; i < products.size(); i++) {
            title = products.get(i).getProductName();
            productId = products.get(i).getProductId();
            thumbnail = imageRepository.findByProductId(productId).getThumbnail();
            price = productInfoRepository.findByProductId(productId).getPrice();
            wish = productInfoRepository.findByProductId(productId).getWish();

            ProductListResponse productListResponse = new ProductListResponse(title, thumbnail, price, productId, wish);
            productListResponses.add(productListResponse);
        }
        return productListResponses;
    }

    @Override
    public ProductReadResponse read(Long productId) {
        Optional<Product> maybeProduct = productRepository.findByProductId(productId);
        Optional<Image> maybeImage = imageRepository.findByProduct_ProductId(productId);
        Optional<ProductInfo> maybeProductInfo = productInfoRepository.findByProduct_ProductId(productId);

        if (maybeProduct.isEmpty()) {
            log.info("읽을 수가 없습니다.");
            return null;
        }

        Product product = maybeProduct.get();
        Image image = maybeImage.get();
        ProductInfo productInfo = maybeProductInfo.get();

        ProductReadResponse productReadResponse = new ProductReadResponse(
                product.getProductId(), productInfo.getPrice(), productInfo.getWish(), product.getProductName(),
                image.getThumbnail(), productInfo.getProductSubName(), image.getDetail()
        );

        return productReadResponse;
    }

    public void register(List<MultipartFile> imageFileList, ProductRegisterRequest productRegisterRequest) {
        Product product = productRegisterRequest.toProduct(); // Create Product
        ProductInfo productInfo = productRegisterRequest.toProductInfo(); // Create ProductInfo
        List<String> categories = productRegisterRequest.getCategories();

        List<ProductOption> productOptionList = categories.stream()
                .map(name -> categoryRepository.findByCategoryName(name))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ProductOption::new)
                .collect(Collectors.toList());

        productOptionList.forEach(productOption -> {
            productOption.setProduct(product);
        });


        productInfo.setProduct(product);

        String thumbnail = imageFileList.get(0).getName();
        String detail = imageFileList.get(1).getName();
        Image image = new Image(thumbnail, detail); // Create Image
        image.setProduct(product);

        // Deep Copy to Frontend Server File System
        final String fixedStringPath = "../SBSJ-Front/sbsj_web/src/assets/productImgs/";

        try {
            log.info("requestFileUploadWithText() - Filename: " + thumbnail);
            Path currentPath = Paths.get("").toAbsolutePath();
            log.info("Current relative path: {}", currentPath);
            FileOutputStream writer = new FileOutputStream(fixedStringPath + thumbnail);
            writer.write(imageFileList.get(0).getBytes()); // save thumbnail image

            log.info("requestFileUploadWithText() - Filename: " + detail);
            writer = new FileOutputStream(fixedStringPath + detail);
            writer.write(imageFileList.get(1).getBytes()); // save detail image
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Saving at each Repository
        productRepository.save(product);
        imageRepository.save(image);
        productInfoRepository.save(productInfo);
        productOptionRepository.saveAll(productOptionList);
    }
}
