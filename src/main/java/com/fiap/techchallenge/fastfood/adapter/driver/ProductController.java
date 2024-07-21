package com.fiap.techchallenge.fastfood.adapter.driver;

import com.fiap.techchallenge.fastfood.adapter.driver.dtos.ProductDto;
import com.fiap.techchallenge.fastfood.adapter.driver.mappers.ProductMapperDto;
import com.fiap.techchallenge.fastfood.core.applications.ports.ProductServicePort;
import com.fiap.techchallenge.fastfood.core.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductServicePort productServicePort;

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> findByCategoryId(@RequestParam Long categoryId) {
        List<Product> products = productServicePort.findByCategoryId(categoryId);
        List<ProductDto> productDtos = products.stream().map(ProductMapperDto::toDto).toList();

        return ResponseEntity.status(HttpStatus.OK).body(productDtos);
    }

}
