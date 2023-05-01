package com.example.sbsj_process.category.service;

import com.example.sbsj_process.category.entity.Brand;
import com.example.sbsj_process.category.service.response.ProductListResponse;
import com.example.sbsj_process.category.entity.Category;
import com.example.sbsj_process.category.entity.ProductOption;
import com.example.sbsj_process.category.repository.CategoryRepository;
import com.example.sbsj_process.category.repository.ProductOptionRepository;
import com.example.sbsj_process.product.entity.Product;
import com.example.sbsj_process.product.entity.ProductInfo;
import com.example.sbsj_process.product.repository.ImageRepository;
import com.example.sbsj_process.product.repository.ProductInfoRepository;
import com.example.sbsj_process.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
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
    private List<ProductListResponse> totalProductCache = new ArrayList<>();

    public void addCategory(String category) {
        Category productCategory = new Category(category);
        categoryRepository.save(productCategory);
    }

    //callee
    public List<ProductListResponse> getProductList(List<Product> products) {
        List<ProductListResponse> productListResponses = new ArrayList<>();

        String title, thumbnail;
        Long productId, price, wishCount;
        ProductInfo productInfo;


        for (Product product : products) {
            title = product.getProductName();
            productId = product.getProductId();
            thumbnail = imageRepository.findByProductId(productId).getThumbnail();
            productInfo = productInfoRepository.findByProductId(productId);
            price = productInfo.getPrice();
            Brand brand = productInfo.getBrand();
            String realBrand = brand.getBrandName();
            wishCount = productInfo.getWishCount();
            List<String> productOptions = productOptionRepository.findProductOptionListWithProductId(productId)
                    .stream()
                    .map(ProductOption::getCategory)
                    .map(Category::getCategoryName)
                    .collect(Collectors.toList());

            ProductListResponse productListResponse = new ProductListResponse(title, thumbnail, price, productId, wishCount, productOptions, realBrand);
            productListResponses.add(productListResponse);
        }
        return productListResponses;
    }

    @Cacheable(value = "totalProductList")
    public List<ProductListResponse> getDefaultList() {
        if (this.totalProductCache.size() != 0) {
            log.info("cache used");
            return this.totalProductCache;
        }
        log.info("cache not used");
        List<Product> products = productRepository.findAll();
        this.totalProductCache = getProductList(products);
        return getProductList(products);
    }

    public List<ProductListResponse> getDefaultPartialList(int startIndex, int endIndex) {

        List<ProductListResponse> productListResponses = getDefaultList();
        int size = productListResponses.size();
        log.info("found product: " + size);
        if(size >= endIndex) {
            return productListResponses.subList(startIndex, endIndex);
        } else if(size >= startIndex) {
            return productListResponses.subList(startIndex, size);
        } else {
            log.info("index out of bound");
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    public List<ProductListResponse> getProductWithOption(String optionName) throws RuntimeException {
        Optional<Category> maybeCategory = categoryRepository.findByCategoryName(optionName);
        Category category;
        if(maybeCategory.isPresent()) {
            category = maybeCategory.get();
        } else {
            log.info("there is no such productOption");
            throw new NullPointerException("there is no such productOption");
        }

        List<Product> productList = productOptionRepository.findProductOptionListWithCategoryId(category.getCategoryId())
                .stream()
                .map(ProductOption::getProduct)
                .collect(Collectors.toList());
        return getProductList(productList);
    }

    public List<ProductListResponse> getProductSpecificList(String optionName, int startIndex, int endIndex) {
        List<ProductListResponse> productListResponses;
        if (this.totalProductCache.size() > 0) {
            productListResponses = getDefaultList()
                    .stream()
                    .filter(productResponse -> productResponse.getProductOptions().contains(optionName))
                    .collect(Collectors.toList());
        } else {
            productListResponses = getProductWithOption(optionName);
        }
        int size = productListResponses.size();
        if(size >= endIndex) {
            return productListResponses.subList(startIndex, endIndex);
        } else if(size >= startIndex) {
            return productListResponses.subList(startIndex, size);
        } else {
            log.info("index out of bound");
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    public List<ProductListResponse> getProductSpecificBrandList(String brand, int startIndex, int endIndex) {
        List<ProductListResponse> productListResponses;
        if (this.totalProductCache.size() > 0) {
            productListResponses = getDefaultList()
                    .stream()
                    .filter(productResponse -> productResponse.getBrand().equals(brand))
                    .collect(Collectors.toList());
        } else {
            List<Product> products = productInfoRepository.findByBrandName(brand)
                                        .stream()
                                        .map(ProductInfo::getProduct)
                                        .collect(Collectors.toList());
            int size = products.size();
            if(size >= endIndex) {
                return getProductList(products.subList(startIndex, endIndex));
            } else if(size >= startIndex) {
                return getProductList(products.subList(startIndex, size));
            } else {
                log.info("index out of bound");
                throw new IndexOutOfBoundsException("Index out of bounds");
            }
        }
        int size = productListResponses.size();
        if(size >= endIndex) {
            return productListResponses.subList(startIndex, endIndex);
        } else if(size >= startIndex) {
            return productListResponses.subList(startIndex, size);
        } else {
            log.info("index out of bound");
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    public List<ProductListResponse> getProductWithSearchQuery(List<String> query, int startIndex, int endIndex) {
        log.info("getProductWithSearchQuery()");
        if (query == null) {
            return null;
        }
        if (query.size() >= 1) {
            Set<Product> sum = new HashSet<>();
            Set<ProductListResponse> cachedSum = new HashSet<>();
            for (String s : query) {
                if (this.totalProductCache.size() > 0) {
                     cachedSum.addAll(getDefaultList()
                             .stream()
                             .filter(productResponse -> productResponse.getTitle().contains(s))
                             .collect(Collectors.toSet()));
                } else {
                    List<Product> products = productRepository.findByProductNameContaining(s);
                    if (products.size() >= 1) {
                        sum.addAll(new HashSet<>(products));
                    }
                }
            }
            if (this.totalProductCache.size() > 0) {
                List<ProductListResponse> productListResponses = new ArrayList<>(cachedSum);
                log.info("found item: " + productListResponses.size());
                int size = productListResponses.size();
                if (size >= endIndex) {
                    return productListResponses.subList(startIndex, endIndex);
                } else if (size >= startIndex) {
                    return productListResponses.subList(startIndex, size);
                } else {
                    log.info("not found anything");
                    return null; //not found anything
                }
            } else {
                List<Product> productList = new ArrayList<>(sum);
                if (productList.size() >= endIndex) {
                    log.info("found product: " + productList.size());
                    return getProductList(productList.subList(startIndex, endIndex));
                } else if(productList.size() > startIndex) {
                    log.info("found product: " + productList.size());
                    return getProductList(productList.subList(startIndex, productList.size()));
                } else {
                    log.info("not found anything");
                    return null; //not found anything
                }
            }

        }
        return null;
    }
}
