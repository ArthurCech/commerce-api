package io.github.arthurcech.orderscrudcommerce.dto.user;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.arthurcech.orderscrudcommerce.entity.enums.Role;

@JsonPropertyOrder({"id", "name", "email", "phone", "password", "role"})
public record UserResponse(
        Long id,
        String name,
        String email,
        String phone,
        Role role
) {
}
