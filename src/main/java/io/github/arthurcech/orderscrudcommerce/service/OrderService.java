package io.github.arthurcech.orderscrudcommerce.service;

import io.github.arthurcech.orderscrudcommerce.dto.order.OrderPayload;
import io.github.arthurcech.orderscrudcommerce.dto.order.OrderResponse;
import io.github.arthurcech.orderscrudcommerce.dto.order.PaymentPayload;
import io.github.arthurcech.orderscrudcommerce.entity.Client;
import io.github.arthurcech.orderscrudcommerce.entity.Order;
import io.github.arthurcech.orderscrudcommerce.entity.OrderItem;
import io.github.arthurcech.orderscrudcommerce.entity.Product;
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
    public OrderResponse insert(OrderPayload payload) {
        try {
            Order order = createOrder(payload);
            payload.items().forEach(item -> {
                Product product = productRepository.getReferenceById(item.productId());
                OrderItem orderItem = new OrderItem(
                        order,
                        product,
                        item.quantity(),
                        item.price()
                );
                orderItemRepository.save(orderItem);
                order.getItems().add(orderItem);
            });
            return OrderMapper.INSTANCE.toOrderResponse(order);
        } catch (EntityNotFoundException e) {
            throw new DomainNotFoundException(PRODUCT_NOT_FOUND);
        }
    }

    @Transactional
    protected Order createOrder(OrderPayload payload) {
        try {
            Client client = clientRepository.getReferenceById(payload.clientId());
            Order order = new Order();
            order.setMoment(payload.moment());
            order.setClient(client);
            order.setOrderStatus(payload.orderStatus());
            return orderRepository.save(order);
        } catch (DataAccessException e) {
            throw new DomainNotFoundException(CLIENT_NOT_FOUND);
        }
    }

    @Transactional
    public OrderResponse payment(Long id, PaymentPayload payload) {
        try {
            Order order = orderRepository.getReferenceById(id);
            order.setOrderStatus(payload.orderStatus());
            order = orderRepository.save(order);
            return OrderMapper.INSTANCE.toOrderResponse(order);
        } catch (EntityNotFoundException e) {
            throw new DomainNotFoundException(ORDER_NOT_FOUND);
        }
    }

    @Transactional
    public OrderResponse update(Long id, OrderPayload payload) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DomainNotFoundException(ORDER_NOT_FOUND));
        if (!order.getClient().getId().equals(payload.clientId())) {
            Client client = clientRepository.findById(payload.clientId())
                    .orElseThrow(() -> new DomainNotFoundException(CLIENT_NOT_FOUND));
            order.setClient(client);
        }
        order.setMoment(payload.moment());
        order.setOrderStatus(payload.orderStatus());
        orderRepository.save(order);
        return OrderMapper.INSTANCE.toOrderResponse(order);
    }

}
