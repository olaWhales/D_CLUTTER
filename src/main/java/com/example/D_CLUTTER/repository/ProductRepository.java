package com.example.D_CLUTTER.repository;

import com.example.D_CLUTTER.data.Product;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @NotNull Optional<Product> findById(@NotNull Long id);
}
