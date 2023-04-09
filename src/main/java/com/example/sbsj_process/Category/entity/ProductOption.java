package com.example.sbsj_process.Category.entity;

import com.example.sbsj_process.Category.entity.Category;
import com.example.sbsj_process.product.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productOptionId;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "productInfo_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductOption(Category category) {
        this.category = category;
    }
}
