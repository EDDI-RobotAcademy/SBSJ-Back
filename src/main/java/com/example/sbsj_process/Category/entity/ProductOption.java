package com.example.sbsj_process.category.entity;

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
    @JoinColumn(name = "product_id")
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
