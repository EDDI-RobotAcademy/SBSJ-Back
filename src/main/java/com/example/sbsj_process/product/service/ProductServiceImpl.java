package com.example.sbsj_process.product.service;

import com.example.sbsj_process.category.entity.Brand;
import com.example.sbsj_process.category.entity.Category;
import com.example.sbsj_process.category.entity.ProductOption;
import com.example.sbsj_process.category.repository.BrandRepository;
import com.example.sbsj_process.category.repository.CategoryRepository;
import com.example.sbsj_process.category.repository.ProductOptionRepository;
import com.example.sbsj_process.product.service.response.ProductReadResponse;
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
    private final WishRepository wishRepository;
    private final BrandRepository brandRepository;

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

        Product product = maybeProduct.get();
        Image image = maybeImage.get();
        ProductInfo productInfo = maybeProductInfo.get();

        List<Wish> wishList = wishRepository.findByProduct_ProductId(productId);
        Long wishCount = new Long(wishList.size());
        productInfo.setWishCount(wishCount);

        Optional<Wish> maybeWish = wishRepository.findByMember_MemberIdAndProduct_ProductId(memberId, productId);

        Long wishId = null;
        if(maybeWish.isPresent()) {
            wishId = maybeWish.get().getWishId();
        }

        ProductReadResponse productReadResponse = new ProductReadResponse(
                product.getProductId(), productInfo.getPrice(), productInfo.getWishCount(), product.getProductName(),
                image.getThumbnail(), productInfo.getProductSubName(), image.getDetail(), wishId
        );

        return productReadResponse;
    }

    public void register(List<MultipartFile> imageFileList, ProductRegisterRequest productRegisterRequest) {
        Product product = productRegisterRequest.toProduct(); // Create Product
        String brand = productRegisterRequest.getBrand();
        Optional<Brand> maybeBrand = brandRepository.findByBrandName(brand);
        Brand realBrand;
        if(maybeBrand.isPresent()) {
            realBrand = maybeBrand.get();
        } else {
            throw new RuntimeException("there is no such brand");
        }
        ProductInfo productInfo = productRegisterRequest.toProductInfo(); // Create ProductInfo
        List<String> categories = productRegisterRequest.getCategories();

        List<ProductOption> productOptionList = categories.stream()
                .map(name -> categoryRepository.findByCategoryName(name))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(ProductOption::new)
                .collect(Collectors.toList());
        if(productOptionList.size() != categories.size()) {
            throw new RuntimeException("there is something does not match category name in Category database");
        }

        productOptionList.forEach(productOption -> {
            productOption.setProduct(product);
        });

        productInfo.setProduct(product);
        productInfo.setBrand(realBrand);

        String thumbnail = imageFileList.get(0).getOriginalFilename().strip().replaceAll(" ", "_");
        String detail = imageFileList.get(1).getOriginalFilename().strip().replaceAll(" ", "_");
        Image image = new Image(thumbnail, detail); // Create Image
        image.setProduct(product);

        // Deep Copy to Frontend Server File System
        final String fixedStringPath = "../SBSJ-Front/sbsj_web/src/assets/productImgs/";

        try {
            for (MultipartFile multipartFile : imageFileList) {
                String fullPath = fixedStringPath + multipartFile.getOriginalFilename().strip().replaceAll(" ", "_");
                FileOutputStream writer = new FileOutputStream(fullPath);
                writer.write(multipartFile.getBytes()); // save thumbnail image
                writer.close();
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
        // Saving at each Repository

        productRepository.save(product);
        imageRepository.save(image);
        productInfoRepository.save(productInfo);
        productOptionRepository.saveAll(productOptionList);
    }

}

