package com.fiap.techchallenge.fastfood.core.domain;

public class Product {

    public Product(Long id, String name, String description, Category category, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public Product(String name, String description, Category category, Double price) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public Product(Long id) {
        this.id = id;
    }

    private Long id;

    private String name;

    private String description;

    private Category category;

    private Double price;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
