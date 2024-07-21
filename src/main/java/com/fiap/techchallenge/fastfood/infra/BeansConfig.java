package com.fiap.techchallenge.fastfood.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fiap.techchallenge.fastfood.core.applications.ports.CategoryRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.CategoryServicePort;
import com.fiap.techchallenge.fastfood.core.applications.ports.PaymentRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.PaymentServicePort;
import com.fiap.techchallenge.fastfood.core.applications.ports.ProductRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.ProductServicePort;
import com.fiap.techchallenge.fastfood.core.applications.ports.UserRepositoryPort;
import com.fiap.techchallenge.fastfood.core.applications.ports.UserServicePort;
import com.fiap.techchallenge.fastfood.core.applications.services.CategoryService;
import com.fiap.techchallenge.fastfood.core.applications.services.PaymentService;
import com.fiap.techchallenge.fastfood.core.applications.services.ProductService;
import com.fiap.techchallenge.fastfood.core.applications.services.UserService;

@Configuration
public class BeansConfig {

    @Bean
    public UserServicePort userServiceImpl(UserRepositoryPort userRepositoryPort) {
        return new UserService(userRepositoryPort);
    }

    @Bean
    public ProductServicePort productServiceImpl(ProductRepositoryPort prodRepositoryPort) {
        return new ProductService(prodRepositoryPort);
    }

    @Bean
    public CategoryServicePort categoryServiceImpl(CategoryRepositoryPort categoryRepositoryPort) {
        return new CategoryService(categoryRepositoryPort);
    }

    @Bean
    public PaymentServicePort paymentServiceImpl(PaymentRepositoryPort PaymentRepositoryPort) {
        return new PaymentService(PaymentRepositoryPort);
    }

}
