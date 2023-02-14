package io.github.arthurcech.orderscrudcommerce.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.arthurcech.orderscrudcommerce.dto.product.ProductResponse;

@JsonPropertyOrder({"quantity", "price", "subTotal", "product"})
public record OrderItemResponse(
        Integer quantity,
        Double price,
        @JsonProperty("subtotal") Double subTotal,
        ProductResponse product
) {
}
