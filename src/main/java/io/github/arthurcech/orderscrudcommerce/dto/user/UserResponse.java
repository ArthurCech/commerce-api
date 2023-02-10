package io.github.arthurcech.orderscrudcommerce.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.Instant;

@JsonPropertyOrder({"id", "name", "email", "phone", "password", "createdAt", "updatedAt"})
public record UserResponse(
        Long id,
        String name,
        String email,
        String phone,
        String password,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt
) {
}
