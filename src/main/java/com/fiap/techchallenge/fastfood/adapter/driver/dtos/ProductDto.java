package com.fiap.techchallenge.fastfood.adapter.driver.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
    @NotNull private Long id;
    @NotNull private String name;
    @NotNull private String description;
    @NotNull private Long categoryId;
    @NotNull private Double price;
}
