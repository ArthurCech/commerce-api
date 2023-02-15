package io.github.arthurcech.orderscrudcommerce.service;

import io.github.arthurcech.orderscrudcommerce.dto.order.CreateOrderPayload;
import io.github.arthurcech.orderscrudcommerce.dto.order.OrderResponse;
import io.github.arthurcech.orderscrudcommerce.entity.Client;
import io.github.arthurcech.orderscrudcommerce.entity.Order;
import io.github.arthurcech.orderscrudcommerce.entity.OrderItem;
import io.github.arthurcech.orderscrudcommerce.entity.Product;
import io.github.arthurcech.orderscrudcommerce.entity.enums.OrderStatus;
import io.github.arthurcech.orderscrudcommerce.mapper.OrderMapper;
import io.github.arthurcech.orderscrudcommerce.repository.ClientRepository;
import io.github.arthurcech.orderscrudcommerce.repository.OrderItemRepository;
import io.github.arthurcech.orderscrudcommerce.repository.OrderRepository;
import io.github.arthurcech.orderscrudcommerce.repository.ProductRepository;
import io.github.arthurcech.orderscrudcommerce.service.exception.DomainNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static io.github.arthurcech.orderscrudcommerce.service.constant.ExceptionMessages.CLIENT_NOT_FOUND;
import static io.github.arthurcech.orderscrudcommerce.service.constant.ExceptionMessages.ORDER_NOT_FOUND;
import static io.github.arthurcech.orderscrudcommerce.service.constant.ExceptionMessages.PRODUCT_NOT_FOUND;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository,
                        ClientRepository clientRepository,
                        ProductRepository productRepository,
                        OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        List<Order> list = orderRepository.findAll();
        return list.stream().map(OrderMapper.INSTANCE::toOrderResponse).toList();
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DomainNotFoundException(ORDER_NOT_FOUND));
        return OrderMapper.INSTANCE.toOrderResponse(order);
    }

    @Transactional
    public OrderResponse insert(CreateOrderPayload payload) {
        try {
            final Order order = createOrder(payload.clientId(), payload.moment(), payload.orderStatus());
            payload.items().forEach(item -> {
                Product product = productRepository.getReferenceById(item.productId());
                OrderItem orderItem = new OrderItem(
                        order,
                        product,
                        item.quantity(),
                        item.price(),
                        null,
                        null);
                orderItem = orderItemRepository.save(orderItem);
                order.getItems().add(orderItem);
            });
            return OrderMapper.INSTANCE.toOrderResponse(order);
        } catch (EntityNotFoundException e) {
            throw new DomainNotFoundException(PRODUCT_NOT_FOUND);
        }
    }

    private Order createOrder(Long clientId, Instant moment, OrderStatus status) {
        try {
            Client client = clientRepository.getReferenceById(clientId);
            Order order = new Order();
            order.setMoment(moment);
            order.setClient(client);
            order.setOrderStatus(status);
            return orderRepository.save(order);
        } catch (DataAccessException e) {
            throw new DomainNotFoundException(CLIENT_NOT_FOUND);
        }
    }

}
