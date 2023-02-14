package io.github.arthurcech.orderscrudcommerce.service.validation;

import io.github.arthurcech.orderscrudcommerce.controller.exception.FieldMessage;
import io.github.arthurcech.orderscrudcommerce.dto.user.RegisterPayload;
import io.github.arthurcech.orderscrudcommerce.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, RegisterPayload> {

    private final UserRepository userRepository;

    public UserInsertValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(
            RegisterPayload payload,
            ConstraintValidatorContext context
    ) {
        List<FieldMessage> fieldsMessage = new ArrayList<>();
        userRepository.findByEmail(payload.email()).ifPresent(user -> {
            fieldsMessage.add(new FieldMessage("email", "User already exists"));
        });
        for (FieldMessage f : fieldsMessage) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(f.message())
                    .addPropertyNode(f.fieldName())
                    .addConstraintViolation();
        }
        return fieldsMessage.isEmpty();
    }

}
