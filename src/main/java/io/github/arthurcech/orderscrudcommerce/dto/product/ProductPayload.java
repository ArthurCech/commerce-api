package io.github.arthurcech.orderscrudcommerce.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

public record ProductPayload(
        @NotBlank
        @Size(min = 2, max = 255)
        String name,
        @NotBlank
        @Size(min = 10, max = 255)
        String description,
        @NotNull
        @Positive
        Double price,
        @NotBlank
        @JsonProperty("img_url")
        String imgUrl,
        @NotNull
        @Size(min = 1)
        @Valid
        @UniqueElements
        List<ProductCategoryPayload> categories
) {
}
