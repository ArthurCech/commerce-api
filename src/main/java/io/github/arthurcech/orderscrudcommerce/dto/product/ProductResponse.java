package io.github.arthurcech.orderscrudcommerce.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.Instant;
import java.util.List;

@JsonPropertyOrder({"id", "name", "description", "price", "imgUrl", "categories", "createdAt", "updatedAt"})
public record ProductResponse(
        Long id,
        String name,
        String description,
        Double price,
        String imgUrl,
        List<ProductCategoryResponse> categories,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt
) {
}
