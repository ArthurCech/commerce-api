package io.github.arthurcech.orderscrudcommerce.dto.auth;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.arthurcech.orderscrudcommerce.entity.enums.Role;

@JsonPropertyOrder({"name", "email", "phone", "role"})
public record AuthenticationResponse(
        String name,
        String email,
        String phone,
        Role role
) {
}
