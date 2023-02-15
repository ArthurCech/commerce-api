package io.github.arthurcech.orderscrudcommerce.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordPayload(
        @NotBlank @Size(max = 16) String password
) {
}
