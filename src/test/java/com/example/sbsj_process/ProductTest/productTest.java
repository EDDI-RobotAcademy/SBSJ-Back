package com.example.sbsj_process.ProductTest;


import com.example.sbsj_process.category.entity.Brand;
import com.example.sbsj_process.category.entity.Category;
import com.example.sbsj_process.category.entity.ProductOption;
import com.example.sbsj_process.category.repository.BrandRepository;
import com.example.sbsj_process.category.repository.CategoryRepository;
import com.example.sbsj_process.category.repository.ProductOptionRepository;
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
import java.util.*;


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
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Test
    public void 상품_등록_테스트() throws Exception {
        File[] files = new File[2];
        files[0] = new File("/Users/namgunho/git/sbsj/SBSJ-Back/src/test/java/com/example/sbsj_process/ProductTest/testImage/PERNIT.jpg");
        files[1] = new File("/Users/namgunho/git/sbsj/SBSJ-Back/src/test/java/com/example/sbsj_process/ProductTest/testImage/PERNIT_Detail.jpg");

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



//        String productName = "좋은약";
        String productName = "약약약";
        String productSubName = "약약약약약";
        String brand = "ihub";
        Long productPrice = 10000L;
        List<String> categorys = new ArrayList<>();
        categorys.add("EYE");
//        categorys.add("BONE");
//        categorys.add("VITAMIN-C");
        categorys.add("VITAMIN-D");
        categorys.add("STRESS");
        ProductRegisterForm productRegisterForm = new ProductRegisterForm(productName, productSubName, productPrice, categorys, brand);
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

    @Test
    public void 랜덤_상품_등록() throws Exception {
        String[] categories = {"EYE", "BONE", "VITAMIN-C", "VITAMIN-D", "STRESS"};
        String[] brands = {"ABC", "DEF", "GHI", "JKL", "MNO"};
        Set productTitleList = new HashSet<String>();
        Random random = new Random();
        String thumbnail = "PERNIT.jpg";
        String detail = "PERNIT_Detail.jpg";
        List<Product> productList = new ArrayList<>();
        List<ProductInfo> productInfoList = new ArrayList<>();
        List<ProductOption> productOptionList = new ArrayList<>();
        List<Image> imageList = new ArrayList<>();

        int k = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 80; i++) {
            int titleLength = random.nextInt(5);
            for(int j = 0; j <= titleLength; j++) {
                k = random.nextInt(5);
                sb.append(categories[k]);
            }

            Product p;
            if (sb.toString().length() < 16) {
                if(productTitleList.contains(sb.toString())) {
                    sb.setLength(0);
                    continue;
                }
                p = new Product(sb.toString());
                productTitleList.add(sb.toString());
            } else {
                if(productTitleList.contains(sb.toString().substring(0, 15))) {
                    sb.setLength(0);
                    continue;
                }
                p = new Product(sb.substring(0, 15));
                productTitleList.add(sb.substring(0, 15));
            }
            sb.setLength(0);
            long price = random.nextLong() % 500;
            if (price < 0) {
                price += 500;
            }
            ProductInfo pf = new ProductInfo(price, "Test" + i);
            pf.setProduct(p);
            k = random.nextInt(5);
            Brand brand = brandRepository.findByBrandName(brands[k]).get();
            pf.setBrand(brand);
            Image image = new Image(thumbnail, detail);
            image.setProduct(p);
            int optionLength = random.nextInt(5);
            List<Integer> duplicateOptionCheck = new ArrayList<>();
            int q = 0;
            for (int z = 0; z <= optionLength; z++) {
                q = random.nextInt(5);
                if(duplicateOptionCheck.contains(q)){
                    continue;
                }
                duplicateOptionCheck.add(q);
                Category c = categoryRepository.findByCategoryName(categories[q]).get();
                ProductOption po = new ProductOption(c);
                po.setProduct(p);
                productOptionList.add(po);
            }


            productList.add(p);
            imageList.add(image);
            productInfoList.add(pf);
        }
        int size = productList.size();
        System.out.println("register product items: " + size);
        productRepository.saveAll(productList);
        imageRepository.saveAll(imageList);
        productInfoRepository.saveAll(productInfoList);
        productOptionRepository.saveAll(productOptionList);

    }

    @Test
    public void addBrandTest() {
        String[] option_list = new String[5];
        option_list[0] = "ABC";
        option_list[1] = "DEF";
        option_list[2] = "GHI";
        option_list[3] = "JKL";
        option_list[4] = "MNO";
        for(int i = 0; i < option_list.length; i++) {
            productService.addBrand(option_list[i]);
        }
    }
}
