package com.fiap.techchallenge.fastfood.core.domain;

import java.time.LocalDateTime;

public class User {

    private Long id;
    private String name;
    private String email;
    private String cpf;
    private LocalDateTime createdAt;

    public User(Long id, String name, String email, String cpf, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.createdAt = createdAt;
    }

    public User(String name, String email, String cpf, LocalDateTime now) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
