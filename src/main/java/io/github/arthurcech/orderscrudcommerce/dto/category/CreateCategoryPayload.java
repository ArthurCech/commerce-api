package io.github.arthurcech.orderscrudcommerce.dto.category;

import io.github.arthurcech.orderscrudcommerce.service.validation.annotation.CategoryInsertValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@CategoryInsertValid
public record CreateCategoryPayload(
        @NotBlank
        @Size(min = 2, max = 255)
        String name
) {
}
