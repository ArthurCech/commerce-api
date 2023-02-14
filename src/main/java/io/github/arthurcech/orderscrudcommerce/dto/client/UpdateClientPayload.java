package io.github.arthurcech.orderscrudcommerce.dto.client;

import io.github.arthurcech.orderscrudcommerce.service.validation.annotation.ClientUpdateValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@ClientUpdateValid
public record UpdateClientPayload(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String phone
) {
}
