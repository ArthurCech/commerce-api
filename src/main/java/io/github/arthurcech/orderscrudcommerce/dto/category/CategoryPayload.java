package io.github.arthurcech.orderscrudcommerce.dto.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record CategoryPayload(
        @NotBlank(message = "Campo name obrigatório")
        @Size(min = 2, max = 255, message = "O campo name deve ter no mínimo 2 e no máximo 255 caracteres")
        String name
) {
}
