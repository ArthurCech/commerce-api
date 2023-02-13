package io.github.arthurcech.orderscrudcommerce.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.arthurcech.orderscrudcommerce.dto.product.ProductCategoryResponse;

import java.util.List;

@JsonPropertyOrder({"id", "name", "description", "price", "imgUrl", "categories"})
public record OrderProductResponse(
        Long id,
        String name,
        String description,
        Double price,
        @JsonProperty("img_url") String imgUrl,
        List<ProductCategoryResponse> categories
) {
}
