package io.github.arthurcech.orderscrudcommerce.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.arthurcech.orderscrudcommerce.dto.order.item.CreateItemPayload;
import io.github.arthurcech.orderscrudcommerce.entity.enums.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.Instant;
import java.util.List;

public record OrderPayload(
        Instant moment,
        @JsonProperty("status")
        @NotNull
        OrderStatus orderStatus,
        @NotNull @Positive @JsonProperty("client_id")
        Long clientId,
        @NotNull
        @Size(min = 1)
        @UniqueElements
        @Valid
        List<CreateItemPayload> items
) {
}
