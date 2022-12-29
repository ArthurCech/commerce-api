package io.github.arthurcech.orderscrudcommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.arthurcech.orderscrudcommerce.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
