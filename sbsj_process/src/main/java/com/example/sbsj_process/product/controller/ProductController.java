package com.example.sbsj_process.product.controller;

import com.example.sbsj_process.product.controller.form.ProductRegisterForm;
import com.example.sbsj_process.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/product")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {
    final private ProductService productService;

    @PostMapping(value = "/register", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void productRegister(@RequestPart(value = "imageFileList") List<MultipartFile> imageFileList, // imageList 0: thumbnail 1: detail
                                @RequestPart(value = "productInfo") ProductRegisterForm productRegisterForm) { // productName, price
        log.info("productRegister()");
        productService.register(imageFileList, productRegisterForm.toProductRegisterRequest());
    }
}