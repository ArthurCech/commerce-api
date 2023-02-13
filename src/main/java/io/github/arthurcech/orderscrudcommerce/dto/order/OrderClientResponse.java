package io.github.arthurcech.orderscrudcommerce.dto.order;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "name", "email", "phone"})
public record OrderClientResponse(
        Long id,
        String name,
        String email,
        String phone
) {
}
