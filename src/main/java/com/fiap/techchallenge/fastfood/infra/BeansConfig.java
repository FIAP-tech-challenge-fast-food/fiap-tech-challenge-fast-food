package com.fiap.techchallenge.fastfood.infra;

import com.fiap.techchallenge.fastfood.core.applications.ports.*;
import com.fiap.techchallenge.fastfood.core.applications.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public UserServicePort userServiceImpl(UserRepositoryPort userRepositoryPort) {
        return new UserService(userRepositoryPort);
    }

    @Bean
    public ProductServicePort productServiceImpl(ProductRepositoryPort prodRepositoryPort,
            CategoryRepositoryPort categoryRepositoryPort) {
        return new ProductService(prodRepositoryPort, categoryRepositoryPort);
    }

    @Bean
    public CategoryServicePort categoryServiceImpl(CategoryRepositoryPort categoryRepositoryPort) {
        return new CategoryService(categoryRepositoryPort);
    }

    @Bean
    public OrderServicePort orderServiceImpl(OrderRepositoryPort orderRepositoryPort,
                                             OrderItemRepositoryPort orderItemRepositoryPort,
                                             ProductRepositoryPort productRepositoryPort,
                                             CategoryRepositoryPort categoryRepositoryPort,
                                             UserRepositoryPort userRepositoryPort) {
        return new OrderService(orderRepositoryPort, orderItemRepositoryPort, productRepositoryPort,
                categoryRepositoryPort, userRepositoryPort);
    }

    @Bean
    public OrderItemServicePort orderItemServiceImpl(OrderItemRepositoryPort orderItemRepositoryPort, ProductRepositoryPort productRepositoryPort, CategoryRepositoryPort categoryRepositoryPort) {
        return new OrderItemService(orderItemRepositoryPort, productRepositoryPort, categoryRepositoryPort);
    }

    @Bean
    public PaymentServicePort paymentServiceImpl(PaymentRepositoryPort paymentRepositoryPort,
            OrderRepositoryPort orderRepositoryPort) {
        return new PaymentService(paymentRepositoryPort, orderRepositoryPort);
    }

}
