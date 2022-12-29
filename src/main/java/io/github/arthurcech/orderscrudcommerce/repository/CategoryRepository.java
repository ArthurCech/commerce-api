package io.github.arthurcech.orderscrudcommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.arthurcech.orderscrudcommerce.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
