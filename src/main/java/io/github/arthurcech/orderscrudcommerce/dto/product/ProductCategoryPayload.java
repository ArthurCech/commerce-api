package io.github.arthurcech.orderscrudcommerce.dto.product;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record ProductCategoryPayload(
        @NotNull @Positive Long id
) {
}
