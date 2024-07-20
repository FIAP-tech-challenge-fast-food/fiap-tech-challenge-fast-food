package com.fiap.techchallenge.fastfood.adapter.driver.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
    private Long id;
    private String name;
    private CategoryDto category;
    private Double price;
}
