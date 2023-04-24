package com.example.sbsj_process.category.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;

    @Column(unique = true)
    private String brandName;

    public Brand(String brand) {
        this.brandName = brand;
    }
}
