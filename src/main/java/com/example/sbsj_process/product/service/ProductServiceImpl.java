package com.example.sbsj_process.product.service;

import com.example.sbsj_process.category.entity.Brand;
import com.example.sbsj_process.category.entity.Category;
import com.example.sbsj_process.category.entity.ProductOption;
import com.example.sbsj_process.category.repository.BrandRepository;
import com.example.sbsj_process.category.repository.CategoryRepository;
import com.example.sbsj_process.category.repository.ProductOptionRepository;
import com.example.sbsj_process.category.service.CategoryService;
import com.example.sbsj_process.category.service.response.ProductListResponse;
import com.example.sbsj_process.product.service.request.ProductModifyRequest;
import com.example.sbsj_process.product.service.request.ProductRegisterRequestForTest;
import com.example.sbsj_process.product.service.response.ProductModifyFormResponse;
import com.example.sbsj_process.product.service.response.ProductReadResponse;
import com.example.sbsj_process.product.entity.*;
import com.example.sbsj_process.product.repository.*;
import com.example.sbsj_process.product.service.request.ProductRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
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
    private final WishRepository wishRepository;
    private final BrandRepository brandRepository;

    private final CategoryService categoryService;


    public List<String> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getCategoryName)
                .map(String::new)
                .collect(Collectors.toList());
    }

    public List<String> getBrands() {
        return brandRepository.findAll()
                .stream()
                .map(Brand::getBrandName)
                .map(String::new)
                .collect(Collectors.toList());
    }

    public void addBrand(String brand) {
        Optional<Brand> maybeBrand = brandRepository.findByBrandName(brand);
        if(maybeBrand.isEmpty()) {
            Brand realBrand = new Brand(brand);
            brandRepository.save(realBrand);
        } else {
            System.out.println("this brand name is already exist");
        }
    }

    @Override
    public ProductReadResponse read(Long memberId, Long productId) {
        Optional<Product> maybeProduct = productRepository.findByProductId(productId);
        Optional<Image> maybeImage = imageRepository.findByProduct_ProductId(productId);
        Optional<ProductInfo> maybeProductInfo = productInfoRepository.findByProduct_ProductId(productId);

        if (maybeProduct.isEmpty()) {
            log.info("읽을 수가 없습니다.");
            return null;
        }
        Image image;
        Product product = maybeProduct.get();
        if (maybeImage.isPresent()) {
            image = maybeImage.get();
        } else {
            log.info("No image was found with that productId");
            return null;
        }

        List<Wish> wishList = wishRepository.findByProduct_ProductId(productId);
        Long wishCount = (long) wishList.size();
        ProductInfo productInfo;
        if (maybeProductInfo.isPresent()) {
            productInfo = maybeProductInfo.get();
            productInfo.setWishCount(wishCount);
        } else {
            log.info("No productInfo was found with that productId");
            return null;
        }

        Optional<Wish> maybeWish = wishRepository.findByMember_MemberIdAndProduct_ProductId(memberId, productId);

        Long wishId = null;
        if(maybeWish.isPresent()) {
            wishId = maybeWish.get().getWishId();
        }

        return new ProductReadResponse(
                product.getProductId(), productInfo.getPrice(), productInfo.getWishCount(), product.getProductName(),
                image.getThumbnail(), productInfo.getProductSubName(), image.getDetail(), wishId);
    }

    public void register(MultipartFile thumbnail, MultipartFile detail, ProductRegisterRequest productRegisterRequest) {
        Product product = productRegisterRequest.toProduct(); // Create Product
        String brand = productRegisterRequest.getBrand();
        Optional<Brand> maybeBrand = brandRepository.findByBrandName(brand);
        Brand realBrand;
        if(maybeBrand.isPresent()) {
            realBrand = maybeBrand.get();
        } else {
            throw new RuntimeException("No brand was found with that brand");
        }
        ProductInfo productInfo = productRegisterRequest.toProductInfo(); // Create ProductInfo
        List<String> categories = productRegisterRequest.getCategories();

        List<ProductOption> productOptionList = categories.stream()
                .map(categoryRepository::findByCategoryName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ProductOption::new)
                .collect(Collectors.toList());
        if(productOptionList.size() != categories.size()) {
            throw new RuntimeException("there is something does not match category name in Category database");
        }

        for (ProductOption productOption : productOptionList) {
            productOption.setProduct(product);
        }

        productInfo.setProduct(product);
        productInfo.setBrand(realBrand);

        String thumbnailImage = Objects.requireNonNull(thumbnail.getOriginalFilename()).strip().replaceAll(" ", "_");
        String detailImage = Objects.requireNonNull(detail.getOriginalFilename()).strip().replaceAll(" ", "_");
        Image image = new Image(thumbnailImage, detailImage); // Create Image
        image.setProduct(product);

        // Deep Copy to Frontend Server File System
        final String fixedStringPath = "../SBSJ-Front/src/assets/productImgs/";

        try {
            String fullPath = fixedStringPath + thumbnail.getOriginalFilename().strip().replaceAll(" ", "_");
            FileOutputStream writer = new FileOutputStream(fullPath);
            writer.write(thumbnail.getBytes()); // save thumbnail image

            fullPath = fixedStringPath + detail.getOriginalFilename().strip().replaceAll(" ", "_");
            writer = new FileOutputStream(fullPath);
            writer.write(detail.getBytes()); // save detail image

            writer.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        // Saving at each Repository
        productRepository.save(product);
        imageRepository.save(image);
        productInfoRepository.save(productInfo);
        productOptionRepository.saveAll(productOptionList);
        List<String> productOptions = productOptionList.stream().map(ProductOption::getCategory).map(Category::getCategoryName).collect(Collectors.toList());
        ProductListResponse productListResponse = new ProductListResponse(product.getProductName(), thumbnailImage, productInfo.getPrice(), product.getProductId(), productInfo.getWishCount(), productOptions, productInfo.getBrand().getBrandName());
        List<ProductListResponse> caching = categoryService.getTotalProductCache();
        if (caching.isEmpty()) {
            categoryService.getDefaultList();
        } else {
            caching.add(productListResponse);
        }
        log.info("product added");
    }

    public void registerForTest(ProductRegisterRequestForTest productRegisterRequestForTest) {
        Product product = productRegisterRequestForTest.toProduct(); // Create Product
        String brand = productRegisterRequestForTest.getBrand();
        Optional<Brand> maybeBrand = brandRepository.findByBrandName(brand);
        Brand realBrand;
        if(maybeBrand.isPresent()) {
            realBrand = maybeBrand.get();
        } else {
            throw new RuntimeException("No brand was found with that brand");
        }
        ProductInfo productInfo = productRegisterRequestForTest.toProductInfo(); // Create ProductInfo
        List<String> categories = productRegisterRequestForTest.getCategories();
        Image tempImage = productRegisterRequestForTest.toImage();


        List<ProductOption> productOptionList = categories.stream()
                .map(categoryRepository::findByCategoryName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ProductOption::new)
                .collect(Collectors.toList());
        if(productOptionList.size() != categories.size()) {
            throw new RuntimeException("there is something does not match category name in Category database");
        }

        for (ProductOption productOption : productOptionList) {
            productOption.setProduct(product);
        }

        productInfo.setProduct(product);
        productInfo.setBrand(realBrand);

        String thumbnailImage = Objects.requireNonNull(tempImage.getThumbnail().strip().replaceAll(" ", "_"));
        String detailImage = Objects.requireNonNull(tempImage.getDetail().strip().replaceAll(" ", "_"));
        Image image = new Image(thumbnailImage, detailImage); // Create Image
        image.setProduct(product);

        // Deep Copy to Frontend Server File System
//        final String fixedStringPath = "../SBSJ-Front/src/assets/productImgs/";
//
//        try {
//            String fullPath = fixedStringPath + thumbnail.getOriginalFilename().strip().replaceAll(" ", "_");
//            FileOutputStream writer = new FileOutputStream(fullPath);
//            writer.write(thumbnail.getBytes()); // save thumbnail image
//
//            fullPath = fixedStringPath + detail.getOriginalFilename().strip().replaceAll(" ", "_");
//            writer = new FileOutputStream(fullPath);
//            writer.write(detail.getBytes()); // save detail image
//
//            writer.close();
//        } catch(IOException e){
//            e.printStackTrace();
//        }
        // Saving at each Repository
        productRepository.save(product);
        imageRepository.save(image);
        productInfoRepository.save(productInfo);
        productOptionRepository.saveAll(productOptionList);
//        List<String> productOptions = productOptionList.stream().map(ProductOption::getCategory).map(Category::getCategoryName).collect(Collectors.toList());
//        ProductListResponse productListResponse = new ProductListResponse(product.getProductName(), thumbnailImage, productInfo.getPrice(), product.getProductId(), productInfo.getWishCount(), productOptions, productInfo.getBrand().getBrandName());
//        List<ProductListResponse> caching = categoryService.getTotalProductCache();
//        if (caching.isEmpty()) {
//            categoryService.getDefaultList();
//        } else {
//            caching.add(productListResponse);
//        }
//        log.info("product added");
    }

    @Transactional
    public void modify(Long productId, MultipartFile thumbnail, MultipartFile detail, ProductModifyRequest productModifyRequest) {
        Product beforeProduct;
        Optional<Product> maybeProduct = productRepository.findByProductId(productId);
        if (maybeProduct.isPresent()) {
            beforeProduct = maybeProduct.get();
        } else {
            log.info("No product was found with that productId");
            return;
        }
        Product afterProduct = productModifyRequest.toProduct();
        ProductInfo afterProductInfo = productModifyRequest.toProductInfo();
        ProductInfo beforeProductInfo = productInfoRepository.findByProductId(productId);
        String beforeBrand = beforeProductInfo.getBrand().getBrandName();
        String afterBrand = productModifyRequest.getBrand();
        List<ProductOption> afterProductOptions = productModifyRequest.getCategories() // have to save
                                .stream()
                                .map(categoryRepository::findByCategoryName)
                                .filter(Optional::isPresent) // new Category cause error, "object references an unsaved transient instance - save the transient instance before flushing"
                                .map(Optional::get)
                                .map(ProductOption::new)
                                .collect(Collectors.toList());

        for (ProductOption productOption : afterProductOptions) {
            productOption.setProduct((beforeProduct));
        }

        // modify old to new
        if(!beforeBrand.equals(afterBrand)) {
            if(brandRepository.findByBrandName(afterBrand).isPresent()) {
                Brand brand = brandRepository.findByBrandName(afterBrand).get();
                beforeProductInfo.setBrand(brand);
            } else {
                log.info("No brand was found with that brand");
                return;
            }
        }
        beforeProduct.modify(afterProduct); // have to save
        beforeProductInfo.modify(afterProductInfo); // have to save
        Image image = imageRepository.findByProductId(productId); // have to save
        final String fixedStringPath = "../SBSJ-Front/src/assets/productImgs/";
        if (thumbnail != null) {
            String oldThumbnailName = image.getThumbnail();
            String newThumbnailName = Objects.requireNonNull(thumbnail.getOriginalFilename()).strip().replaceAll(" ", "_");
            image.setThumbnail(newThumbnailName);
            try {
                String fullPath = fixedStringPath + newThumbnailName;
                FileOutputStream writer = new FileOutputStream(fullPath);
                writer.write(thumbnail.getBytes()); // save thumbnail image
                writer.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
            List<Image> thumbnails = imageRepository.findByThumbnail(oldThumbnailName);
            if (thumbnails.size() <= 1) { // if thumbnail image only referenced by one deleted product
                File beforeThumbnail = new File(fixedStringPath + oldThumbnailName);
                if(beforeThumbnail.delete()) {
                    log.info("thumbnail image deleted");
                } else {
                    log.info("thumbnail image not deleted");
                }
            }
        }
        if (detail != null) {
            String oldDetailName = image.getDetail();
            String newDetailName = Objects.requireNonNull(detail.getOriginalFilename()).strip().replaceAll(" ", "_");
            image.setDetail(newDetailName);
            try {
                String fullPath = fixedStringPath + newDetailName;
                FileOutputStream writer = new FileOutputStream(fullPath);
                writer.write(detail.getBytes()); // save thumbnail image
                writer.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
            List<Image> details = imageRepository.findByDetail(oldDetailName);
            if (details.size() <= 1) { // if detail image only referenced by one deleted product
                File beforeDetail = new File(fixedStringPath + oldDetailName);
                if(beforeDetail.delete()) {
                    log.info("detail image deleted");
                } else {
                    log.info("detail image not deleted");
                }
            }
        }

        productOptionRepository.deleteByProductId(productId);
        productRepository.save(beforeProduct);
        productInfoRepository.save(beforeProductInfo);
        imageRepository.save(image);
        productOptionRepository.saveAll(afterProductOptions);

        log.info("modify complete and start to change caching data");
        List<ProductListResponse> caching = categoryService.getTotalProductCache();
        if (caching.isEmpty()) {
            categoryService.getDefaultList();
        } else {
            ProductListResponse target;
            for (ProductListResponse cache : caching) {
                if(cache.getProductId().equals(productId)) {
                    target = cache;
                    caching.remove(target);
                    ProductListResponse newCache = new ProductListResponse(beforeProduct.getProductName(),
                            image.getThumbnail(),
                            beforeProductInfo.getPrice(),
                            productId,
                            beforeProductInfo.getWishCount(),
                            productModifyRequest.getCategories(),
                            afterBrand);
                    caching.add(newCache);
                    log.info("caching data has been changed");
                    return;
                }
            }
            log.info("No caching data was found with that product");
        }
    }

    @Transactional
    public void delete(Long productId) {
        productOptionRepository.deleteByProductId(productId);
        productInfoRepository.deleteByProductId(productId);
        Image image = imageRepository.findByProductId(productId);
        String thumbnail = image.getThumbnail();
        String detail = image.getDetail();
        wishRepository.deleteByProductId(productId);
        productRepository.deleteByProductId(productId);

        // delete image file
        imageRepository.deleteByProductId(productId);
        List<Image> thumbnails = imageRepository.findByThumbnail(thumbnail);
        List<Image> details = imageRepository.findByDetail(detail);
        final String fixedStringPath = "../SBSJ-Front/src/assets/productImgs/";
        if (thumbnails.size() <= 1) { // if thumbnail image only referenced by one deleted product
            File beforeThumbnail = new File(fixedStringPath + thumbnail);
            if(beforeThumbnail.delete()) {
                log.info("thumbnail image deleted");
            } else {
                log.info("thumbnail image not deleted");
            }
        }
        if (details.size() <= 1) { // if detail image only referenced by one deleted product
            File beforeDetail = new File(fixedStringPath + detail);
            if(beforeDetail.delete()) {
                log.info("detail image deleted");
            } else {
                log.info("detail image not deleted");
            }
        }
        log.info("delete complete and start to change caching data");
        List<ProductListResponse> caching = categoryService.getTotalProductCache();
        if(caching.isEmpty()) {
            categoryService.getDefaultList();
        } else {
            ProductListResponse target;
            for (ProductListResponse cache : caching) {
                if(cache.getProductId().equals(productId)) {
                    target = cache;
                    caching.remove(target);
                    log.info("caching data has been changed");
                    return;
                }
            }
            log.info("No caching data was found with that product");
        }
    }

    public ProductModifyFormResponse getModifyForm(Long productId) {
        Optional<Product> maybeProduct = productRepository.findByProductId(productId);
        Product product;
        if (maybeProduct.isPresent()) {
            product = maybeProduct.get();
        } else {
            log.info("No product was found with that productId");
            return null;
        }
        ProductInfo productInfo = productInfoRepository.findByProductId(productId);
        Image image = imageRepository.findByProductId(productId);
        List<ProductOption> productOptionList = productOptionRepository.findProductOptionListWithProductId(productId);
        return new ProductModifyFormResponse(product.getProductId(),
                        product.getProductName(),
                        productInfo.getPrice(),
                        productInfo.getProductSubName(),
                        productOptionList.stream().map(ProductOption::getCategory).map(Category::getCategoryName).collect(Collectors.toList()),
                        productInfo.getBrand().getBrandName(),
                        image.getThumbnail(),
                        image.getDetail());
    }
}

