package io.github.arthurcech.orderscrudcommerce.dto.category;

import io.github.arthurcech.orderscrudcommerce.service.validation.annotation.CategoryUpdateValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@CategoryUpdateValid
public record UpdateCategoryPayload(
        @NotBlank
        @Size(min = 2, max = 100)
        String name
) {
}
