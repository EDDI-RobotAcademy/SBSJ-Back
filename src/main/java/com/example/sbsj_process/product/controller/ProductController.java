package com.example.sbsj_process.product.controller;


import com.example.sbsj_process.product.controller.form.ProductRegisterFormForTest;
import com.example.sbsj_process.product.service.response.ProductReadResponse;
import com.example.sbsj_process.product.controller.form.ProductRegisterForm;
import com.example.sbsj_process.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/product")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {
    final private ProductService productService;

    @PostMapping(value = "/register", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public void productRegister(@RequestPart(value = "imageFileList") List<MultipartFile> imageFileList, // imageList 0: thumbnail 1: detail
                                @RequestPart(value = "productInfo") ProductRegisterForm productRegisterForm) { // productName, price
        log.info("productRegister()");
        productService.register(thumbnail, detail, productRegisterForm.toProductRegisterRequest());
    }

    @PostMapping(value = "/register/forTest")
    public void productRegisterTest(@RequestPart(value = "productInfo") ProductRegisterFormForTest productRegisterFormForTest) {
        log.info("productRegisterTest()");
        productService.registerForTest(productRegisterFormForTest.toProductRegisterRequestForTest());
    }

    @PutMapping(value = "/modify/{productId}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public void productModify(@RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail,
                                @RequestPart(value = "detail", required = false) MultipartFile detail,
                                @RequestPart(value = "productInfo") ProductRegisterForm productRegisterForm,
                                @PathVariable(value = "productId") Long productId) {
        log.info("productModify()");
        productService.modify(productId, thumbnail, detail, productRegisterForm.toProductModifyRequest());
    }

    @DeleteMapping(value = "/delete/{productId}")
    public void productDelete(@PathVariable(value = "productId") Long productId) {
        log.info("productDelete()");
        productService.delete(productId);
    }

    @GetMapping("/detail-product-page/{productId}/{memberId}")
    public ProductReadResponse productRead(@PathVariable("memberId") Long memberId,
                                           @PathVariable("productId") Long productId) {
        log.info("productRead(): " + memberId + ", " + productId);

        return productService.read(memberId, productId);
    }

    @GetMapping(value = "/productOptions")
    public List<String> getCategories() {
        log.info("getCategories()");
        return productService.getCategories();
    }

    @GetMapping(value = "/productBrands")
    public List<String> getBrands() {
        log.info("getBrands()");
        return productService.getBrands();
    }
}
