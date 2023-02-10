package io.github.arthurcech.orderscrudcommerce.dto.user;

import io.github.arthurcech.orderscrudcommerce.service.validation.UserInsertValid;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@UserInsertValid
public record UserCreatePayload(
        @NotBlank(message = "Campo name obrigatório")
        @Size(min = 2, max = 255, message = "O campo name deve ter no mínimo 2 e no máximo 255 caracteres")
        String name,
        @NotBlank(message = "Campo email obrigatório")
        @Email
        String email,
        @NotBlank(message = "Campo phone obrigatório")
        String phone,
        @NotBlank(message = "Campo password obrigatório")
        @Size(max = 16, message = "O campo senha deve ter no máximo 16 caracteres")
        String password
) {
}
