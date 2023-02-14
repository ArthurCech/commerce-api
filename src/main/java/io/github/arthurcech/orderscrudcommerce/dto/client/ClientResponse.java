package io.github.arthurcech.orderscrudcommerce.dto.client;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "name", "email", "phone"})
public record ClientResponse(
        Long id,
        String name,
        String email,
        String phone
) {
}
