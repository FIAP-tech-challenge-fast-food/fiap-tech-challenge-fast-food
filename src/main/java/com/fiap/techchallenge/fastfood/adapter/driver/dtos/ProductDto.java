package com.fiap.techchallenge.fastfood.adapter.driver.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
    @Nullable
    private Long id;

    @NotNull(message = "Invalid name: cannot be empty")
    @NotEmpty(message = "Invalid name: cannot be empty")
    private String name;

    @Nullable
    private String description;

    @NotNull(message = "Invalid categoryId: cannot be empty")
    private Long categoryId;

    @NotNull
    @Min(value = 0, message = "Invalid price: must be greater or equals 0")
    private Double price;
}
