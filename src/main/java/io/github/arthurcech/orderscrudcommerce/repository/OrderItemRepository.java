package io.github.arthurcech.orderscrudcommerce.repository;

import io.github.arthurcech.orderscrudcommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
