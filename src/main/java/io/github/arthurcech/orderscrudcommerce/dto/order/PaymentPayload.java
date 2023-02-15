package io.github.arthurcech.orderscrudcommerce.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.arthurcech.orderscrudcommerce.entity.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record PaymentPayload(
        @JsonProperty("status")
        @NotNull
        OrderStatus orderStatus
) {
}
