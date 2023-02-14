package io.github.arthurcech.orderscrudcommerce.dto.category;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "name"})
public record CategoryResponse(
        Long id,
        String name
) {
}
