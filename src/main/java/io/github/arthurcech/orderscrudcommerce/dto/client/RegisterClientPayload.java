package io.github.arthurcech.orderscrudcommerce.dto.client;

import io.github.arthurcech.orderscrudcommerce.service.validation.ClientInsertValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@ClientInsertValid
public record RegisterClientPayload(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String phone
) {
}
