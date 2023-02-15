package io.github.arthurcech.orderscrudcommerce.dto.user;

import io.github.arthurcech.orderscrudcommerce.entity.enums.Role;
import io.github.arthurcech.orderscrudcommerce.service.validation.annotation.UserUpdateValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@UserUpdateValid
public record UpdateUserPayload(
        @NotBlank
        @Size(min = 2, max = 255)
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String phone,
        @NotNull
        Role role
) {
}
