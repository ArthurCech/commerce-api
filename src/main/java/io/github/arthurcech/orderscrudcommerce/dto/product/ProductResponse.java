package io.github.arthurcech.orderscrudcommerce.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.arthurcech.orderscrudcommerce.dto.category.CategoryResponse;

import java.math.BigDecimal;
import java.util.List;

@JsonPropertyOrder({"id", "name", "description", "price", "imgUrl", "categories"})
public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        @JsonProperty("img_url") String imgUrl,
        List<CategoryResponse> categories
) {
}
