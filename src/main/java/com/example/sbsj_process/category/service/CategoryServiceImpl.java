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

    //callee
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

    public List<ProductListResponse> getProductWithOption(String optionName) throws RuntimeException {
        Optional<Category> maybeCategory = categoryRepository.findByCategoryName(optionName);
        Category category;
        if(maybeCategory.isPresent()) {
            category = maybeCategory.get();
        } else {
            throw new RuntimeException("there is no such optionName");
        }

        List<Product> productList = productOptionRepository.findProductOptionListWithCategoryId(category.getCategoryId())
                .stream()
                .map(ProductOption::getProduct)
                .collect(Collectors.toList());
        return getProductList(productList);
    }
}
