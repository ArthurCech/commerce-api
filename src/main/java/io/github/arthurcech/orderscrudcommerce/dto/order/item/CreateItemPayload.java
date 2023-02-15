package io.github.arthurcech.orderscrudcommerce.dto.order.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Objects;

public record CreateItemPayload(
        @NotNull @Positive
        Integer quantity,
        @NotNull @Positive
        Double price,
        @JsonProperty("product_id") @NotNull @Positive
        Long productId
) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateItemPayload that = (CreateItemPayload) o;
        return productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
