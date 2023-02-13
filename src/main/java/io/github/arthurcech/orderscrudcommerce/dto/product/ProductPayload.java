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
        @NotBlank(message = "Required field")
        @Size(min = 2, max = 255, message = "The field must have a minimum of 2 and a maximum of 255 characters")
        String name,
        @NotBlank(message = "Required field")
        @Size(min = 10, max = 255, message = "The field must have a minimum of 10 and a maximum of 255 characters")
        String description,
        @NotNull(message = "Required field")
        @Positive(message = "The field should be a positive value")
        Double price,
        @NotBlank(message = "Required field")
        @JsonProperty("img_url")
        String imgUrl,
        @NotNull(message = "Required field")
        @Size(min = 1, message = "The field should has at least 1 category")
        @Valid
        @UniqueElements
        List<ProductCategoryPayload> categories
) {
}
