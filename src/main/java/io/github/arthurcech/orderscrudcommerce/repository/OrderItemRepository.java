package io.github.arthurcech.orderscrudcommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.arthurcech.orderscrudcommerce.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
