package io.github.arthurcech.orderscrudcommerce.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.arthurcech.orderscrudcommerce.entity.enums.OrderStatus;

import java.time.Instant;
import java.util.Set;

@JsonPropertyOrder({"id", "moment", "orderStatus", "client", "items", "total", "payment"})
public record OrderResponse(
        Long id,
        Instant moment,
        @JsonProperty("status") OrderStatus orderStatus,
        OrderClientResponse client,
        Set<OrderItemResponse> items,
        Double total,
        PaymentResponse payment
) {
}
