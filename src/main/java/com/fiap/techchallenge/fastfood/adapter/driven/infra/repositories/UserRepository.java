package com.fiap.techchallenge.fastfood.adapter.driven.infra.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fiap.techchallenge.fastfood.adapter.driven.infra.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE " +
            "(:type = 'email' AND u.email = :value) OR " +
            "(:type = 'cpf' AND u.cpf = :value)")
    Optional<UserEntity> findByIdentifier(@Param("type") String type, @Param("value") String value);
}
