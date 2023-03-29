package com.example.sbsj_process.product.service;

import com.example.sbsj_process.product.entity.Image;
import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.entity.ProductInfo;
import com.example.sbsj_process.product.repository.ImageRepository;
import com.example.sbsj_process.product.repository.ProductInfoRepository;
import com.example.sbsj_process.product.repository.ProductRepository;
import com.example.sbsj_process.product.service.request.ProductRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ProductInfoRepository productInfoRepository;
    private final ImageRepository imageRepository;
    public void register(List<MultipartFile> imageFileList, ProductRegisterRequest productRegisterRequest) {

        Product product = productRegisterRequest.toProduct(); // Create Product

        ProductInfo productInfo = productRegisterRequest.toProductInfo(); // Create ProductInfo
        productInfo.setProduct(product);

        String thumbnail = imageFileList.get(0).getName();
        String detail = imageFileList.get(1).getName();
        Image image = new Image(thumbnail, detail); // Create Image
        image.setProduct(product);


        // Deep Copy to Frontend Server File System
        final String fixedStringPath = "../../SBSJ-Front/sbsj_web/src/assets/productImgs/";
        try {
            log.info("requestFileUploadWithText() - Filename: " + thumbnail);
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
        }

        // Saving at each Repository
        productRepository.save(product);
        imageRepository.save(image);
        productInfoRepository.save(productInfo);
    }
}
