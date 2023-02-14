package io.github.arthurcech.orderscrudcommerce.controller.exception;

public record FieldMessage(
        String fieldName,
        String message
) {
}
