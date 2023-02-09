package io.github.arthurcech.orderscrudcommerce.dto.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.Instant;

@JsonPropertyOrder({"id", "name", "createdAt", "updatedAt"})
public record CategoryResponse(
        Long id,
        String name,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt
) {
}
