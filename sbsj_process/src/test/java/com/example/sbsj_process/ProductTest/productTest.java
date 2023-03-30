package com.example.sbsj_process.ProductTest;


import com.example.sbsj_process.product.controller.form.ProductDefaultResponseForm;
import com.example.sbsj_process.product.controller.form.ProductRegisterForm;
import com.example.sbsj_process.product.entity.Image;
import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.entity.ProductInfo;
import com.example.sbsj_process.product.repository.ImageRepository;
import com.example.sbsj_process.product.repository.ProductInfoRepository;
import com.example.sbsj_process.product.repository.ProductRepository;
import com.example.sbsj_process.product.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class productTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ProductService productService;

    @Test
    public void 기본_상품리스트_가져오기_테스트() throws Exception {
        상품_등록_테스트();
        List<ProductDefaultResponseForm> productDefaultResponseForms = productService.getDefaultList();
        for(int i = 0; i < productDefaultResponseForms.size(); i++) {
            System.out.println("##################################");
            System.out.println(productDefaultResponseForms.get(i));
            System.out.println("##################################");
        }
    }

    @Test
    public void 상품_등록_테스트() throws Exception {
        File[] files = new File[2];
        files[0] = new File("/Users/namgunho/git/sbsj/SBSJ-Back/sbsj_process/src/test/java/com/example/sbsj_process/ProductTest/testImage/PERNIT.jpg");
        files[1] = new File("/Users/namgunho/git/sbsj/SBSJ-Back/sbsj_process/src/test/java/com/example/sbsj_process/ProductTest/testImage/PERNIT_Detail.jpg");
        List<MultipartFile> multipartFiles = new ArrayList<>();
        System.out.println(files.length);
        try {
            for(File file : files) {
                FileInputStream input = new FileInputStream(file);
                MultipartFile multiPartFile = new MockMultipartFile(file.getName(), input.readAllBytes());
                multipartFiles.add(multiPartFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("here");
        }
        System.out.println("multipartFiles.get(0).toString(): " + multipartFiles.get(0).toString());
        System.out.println("multipartFiles.get(1).toString(): " + multipartFiles.get(1).toString());
        String productName = "someProductName";
        Long productPrice = 1000000L;

        ProductRegisterForm productRegisterForm = new ProductRegisterForm(productName, productPrice);
        productService.register(multipartFiles, productRegisterForm.toProductRegisterRequest());
        Optional<Product> mayBeProduct = productRepository.findByProductName(productName);
        Assertions.assertTrue(mayBeProduct.isPresent());
        Product product = mayBeProduct.get();
        System.out.println(product);
        Long product_id = product.getProductId();
        Image mayBeImage = imageRepository.findByProductId(product_id);
        System.out.println(mayBeImage);
        ProductInfo mayBeProductInfo = productInfoRepository.findByProductId(product_id);
        System.out.println(mayBeProductInfo);
    }
}
