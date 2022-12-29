package io.github.arthurcech.orderscrudcommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.arthurcech.orderscrudcommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
