package com.example.sbsj_process.category.service;

import com.example.sbsj_process.category.controller.form.ProductListResponse;
import com.example.sbsj_process.category.entity.Category;
import com.example.sbsj_process.category.entity.ProductOption;
import com.example.sbsj_process.category.repository.CategoryRepository;
import com.example.sbsj_process.category.repository.ProductOptionRepository;
import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.repository.ImageRepository;
import com.example.sbsj_process.product.repository.ProductInfoRepository;
import com.example.sbsj_process.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductOptionRepository productOptionRepository;

    private final ProductInfoRepository productInfoRepository;
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    public void addCategory(String category) {
        Category productCategory = new Category(category);
        categoryRepository.save(productCategory);
    }
    public List<ProductListResponse> getProductList(List<Product> products) {
        List<Product> productList = products;
        List<ProductListResponse> productListResponses = new ArrayList<>();

        String title, thumbnail;
        Long productId, price, wish;

        for(int i = 0; i < productList.size(); i++) {
            title = productList.get(i).getProductName();
            productId = productList.get(i).getProductId();
            thumbnail = imageRepository.findByProductId(productId).getThumbnail();
            price = productInfoRepository.findByProductId(productId).getPrice();
            wish = productInfoRepository.findByProductId(productId).getWish();

            ProductListResponse productListResponse = new ProductListResponse(title, thumbnail, price, productId, wish);
            productListResponses.add(productListResponse);
        }
        return productListResponses;
    }

    public List<ProductListResponse> getDefaultList() {

        List<Product> products = productRepository.findAll();
        return getProductList(products);
    }

    public List<ProductListResponse> getProductWithOptionList(String optionName) {
        List<Category> categories = categoryRepository.findByCategoryName(optionName)
                .stream()
                .collect(Collectors.toList());

        List<Product> productList = productOptionRepository.findByCategoryIn(categories)
                .stream()
                .map(ProductOption::getProduct)
                .collect(Collectors.toList());
        return getProductList(productList);
    }
}
