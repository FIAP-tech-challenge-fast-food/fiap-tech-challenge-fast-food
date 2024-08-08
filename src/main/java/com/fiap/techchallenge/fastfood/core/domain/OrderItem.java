package com.fiap.techchallenge.fastfood.core.domain;

public class OrderItem {

    private Long id;
    private Product product;
    private Order order;
    private Double price;
    private Integer quantity;

    public OrderItem(Long id, Product product, Order order, Double price, Integer quantity) {
        this.id = id;
        this.product = product;
        this.order = order;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderItem(Product product, Order order, Double price, Integer quantity) {
        this.product = product;
        this.order = order;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
