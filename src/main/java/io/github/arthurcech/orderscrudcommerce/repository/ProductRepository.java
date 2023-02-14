package io.github.arthurcech.orderscrudcommerce.repository;

import io.github.arthurcech.orderscrudcommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
