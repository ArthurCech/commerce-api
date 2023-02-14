package io.github.arthurcech.orderscrudcommerce.service;

import io.github.arthurcech.orderscrudcommerce.dto.order.OrderResponse;
import io.github.arthurcech.orderscrudcommerce.entity.Order;
import io.github.arthurcech.orderscrudcommerce.mapper.OrderMapper;
import io.github.arthurcech.orderscrudcommerce.repository.OrderRepository;
import io.github.arthurcech.orderscrudcommerce.service.exception.DomainNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.github.arthurcech.orderscrudcommerce.service.constant.ExceptionMessages.ORDER_NOT_FOUND;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        List<Order> list = repository.findAll();
        return list.stream().map(OrderMapper.INSTANCE::toOrderResponse).toList();
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new DomainNotFoundException(ORDER_NOT_FOUND));
        return OrderMapper.INSTANCE.toOrderResponse(order);
    }

}
