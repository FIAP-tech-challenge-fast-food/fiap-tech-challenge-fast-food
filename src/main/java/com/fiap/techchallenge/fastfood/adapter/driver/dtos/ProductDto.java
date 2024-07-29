package com.fiap.techchallenge.fastfood.adapter.driver.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
    private Long id;

    @NotNull(message = "Invalid name: cannot be empty")
    @NotEmpty(message = "Invalid name: cannot be empty")
    private String name;

    private String description;

    @NotNull(message = "Invalid categoryId: cannot be empty")
    private Long categoryId;

    @NotNull
    @Positive(message = "Invalid price: must be greater or equals 0")
    private Double price;
}
