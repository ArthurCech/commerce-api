package io.github.arthurcech.orderscrudcommerce.dto.user;

import io.github.arthurcech.orderscrudcommerce.service.validation.annotation.UserInsertValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@UserInsertValid
public record RegisterPayload(
        @NotBlank
        @Size(min = 2, max = 255)
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String phone,
        @NotBlank
        @Size(max = 16)
        String password
) {
}
