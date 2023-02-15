package io.github.arthurcech.orderscrudcommerce.controller;

import io.github.arthurcech.orderscrudcommerce.dto.order.CreateOrderPayload;
import io.github.arthurcech.orderscrudcommerce.dto.order.OrderResponse;
import io.github.arthurcech.orderscrudcommerce.dto.order.PaymentPayload;
import io.github.arthurcech.orderscrudcommerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrderResponse> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public OrderResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> insert(
            @RequestBody @Valid CreateOrderPayload payload
    ) {
        OrderResponse response = service.insert(payload);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PatchMapping("/{id}/payment")
    public ResponseEntity<OrderResponse> payment(
            @PathVariable Long id,
            @RequestBody @Valid PaymentPayload payload
    ) {
        OrderResponse response = service.payment(id, payload);
        return ResponseEntity.ok(response);
    }

}
