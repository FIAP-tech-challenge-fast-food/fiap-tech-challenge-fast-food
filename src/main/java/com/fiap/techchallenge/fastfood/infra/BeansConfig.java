package com.fiap.techchallenge.fastfood.infra;

import com.fiap.techchallenge.fastfood.core.applications.ports.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fiap.techchallenge.fastfood.core.applications.services.CategoryService;
import com.fiap.techchallenge.fastfood.core.applications.services.OrderItemService;
import com.fiap.techchallenge.fastfood.core.applications.services.PaymentService;
import com.fiap.techchallenge.fastfood.core.applications.services.ProductService;
import com.fiap.techchallenge.fastfood.core.applications.services.OrderService;
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
    public OrderServicePort orderServiceImpl(OrderRepositoryPort orderRepositoryPort) {
        return new OrderService(orderRepositoryPort);
    }

    @Bean
    public OrderItemServicePort orderItemServiceImpl(OrderItemRepositoryPort orderItemRepositoryPort) {
        return new OrderItemService(orderItemRepositoryPort);
    }

    @Bean
    public PaymentServicePort paymentServiceImpl(PaymentRepositoryPort paymentRepositoryPort, OrderRepositoryPort orderRepositoryPort) {
        return new PaymentService(paymentRepositoryPort, orderRepositoryPort);
    }
    
}
