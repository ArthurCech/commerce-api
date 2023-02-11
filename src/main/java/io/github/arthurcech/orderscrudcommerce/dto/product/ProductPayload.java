package io.github.arthurcech.orderscrudcommerce.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

public record ProductPayload(
        @NotBlank(message = "Campo name obrigatório")
        @Size(min = 2, max = 255, message = "O campo name deve ter no mínimo 2 e no máximo 255 caracteres")
        String name,
        @NotBlank(message = "Campo description obrigatório")
        @Size(min = 10, max = 255, message = "O campo description deve ter no mínimo 10 e no máximo 255 caracteres")
        String description,
        @NotNull(message = "Campo price obrigatório")
        @Positive(message = "O campo price deve ter um valor positivo")
        Double price,
        @NotBlank(message = "Campo imgUrl obrigatório")
        @JsonProperty("img_url")
        String imgUrl,
        @NotNull(message = "Campo categories obrigatório")
        @Size(min = 1, message = "O campo categories deve ter pelo menos 1 item")
        @Valid
        @UniqueElements
        List<CategoryPayload> categories
) {
}
