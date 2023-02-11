package io.github.arthurcech.orderscrudcommerce.dto.product;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record CategoryPayload(
        @NotNull @Positive Long id
) {
}
