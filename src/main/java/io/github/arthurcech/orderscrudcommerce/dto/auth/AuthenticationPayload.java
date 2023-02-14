package io.github.arthurcech.orderscrudcommerce.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationPayload(
        @NotBlank @Email String email,
        @NotBlank String password
) {
}
