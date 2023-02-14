package io.github.arthurcech.orderscrudcommerce.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductCategoryPayload(
        @NotNull @Positive Long id
) {
}
