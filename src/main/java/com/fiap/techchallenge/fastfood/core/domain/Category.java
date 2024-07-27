package com.fiap.techchallenge.fastfood.core.domain;

public class Category {

    private Long id;

    private String description;

    public Category(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Category(Long categoryId) {
        this.id = categoryId;
    }

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
}
