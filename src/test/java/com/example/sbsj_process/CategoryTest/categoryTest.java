package com.example.sbsj_process.CategoryTest;

import com.example.sbsj_process.category.entity.Category;
import com.example.sbsj_process.category.repository.CategoryRepository;
import com.example.sbsj_process.category.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AssertionErrors;

import java.util.Optional;

@SpringBootTest
public class categoryTest {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void addCategoryTest() {
        String[] option_list = new String[5];
        option_list[0] = "EYE";
        option_list[1] = "BONE";
        option_list[2] = "VITAMIN-C";
        option_list[3] = "VITAMIN-D";
        option_list[4] = "STRESS";
        for(int i = 0; i < option_list.length; i++) {
            categoryService.addCategory(option_list[i]);
        }
    }

    @Test
    public void getDefaultListTest() {
        categoryService.getDefaultList()
                .stream()
                .forEach(System.out::println);
    }
    @Test
    public void getCategoryTest() {
        String option = "EYE";
        Optional<Category> maybeCategory = categoryRepository.findByCategoryName(option);
        Category category;
        if (maybeCategory.isPresent()) {
            category = maybeCategory.get();
            System.out.println("category_Id of EYE is: " + category.getCategoryId());
        } else {
            System.out.println("not found");
        }
    }
    @Test
    public void getProductWithOptionListTest() {
        //given
        String option = "STRESS";
        categoryService.getProductWithOption(option)
                .stream()
                .forEach(System.out::println);
        final String notCategory = "TEST";
        Assertions.assertThrows(RuntimeException.class, () -> {
            categoryService.getProductWithOption(notCategory);
        });

    }
}
