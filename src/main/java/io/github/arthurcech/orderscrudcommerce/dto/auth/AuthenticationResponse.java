package io.github.arthurcech.orderscrudcommerce.dto.auth;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"name", "email", "phone"})
public record AuthenticationResponse(
        String name,
        String email,
        String phone
) {
}
