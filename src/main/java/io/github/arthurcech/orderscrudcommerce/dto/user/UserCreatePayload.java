package io.github.arthurcech.orderscrudcommerce.dto.user;

import io.github.arthurcech.orderscrudcommerce.service.validation.UserInsertValid;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@UserInsertValid
public record UserCreatePayload(
        @NotBlank(message = "Required field")
        @Size(min = 2, max = 255, message = "The field must have a minimum of 2 and a maximum of 255 characters")
        String name,
        @NotBlank(message = "Required field")
        @Email
        String email,
        @NotBlank(message = "Required field")
        String phone,
        @NotBlank(message = "Required field")
        @Size(max = 16, message = "The field must have a maximum of 16 characters")
        String password
) {
}
