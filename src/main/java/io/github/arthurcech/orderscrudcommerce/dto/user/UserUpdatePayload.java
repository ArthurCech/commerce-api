package io.github.arthurcech.orderscrudcommerce.dto.user;

import io.github.arthurcech.orderscrudcommerce.entity.enums.Role;
import io.github.arthurcech.orderscrudcommerce.service.validation.UserUpdateValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@UserUpdateValid
public record UserUpdatePayload(
        @NotBlank(message = "Required field")
        @Size(min = 2, max = 255, message = "The field must have a minimum of 2 and a maximum of 255 characters")
        String name,
        @NotBlank(message = "Required field")
        @Email
        String email,
        @NotBlank(message = "Required field")
        String phone,
        @NotBlank(message = "Required field")
        @Size(max = 16, message = "The field must have a maximum of 16 characters")
        String password,
        @NotNull
        Role role
) {
}
