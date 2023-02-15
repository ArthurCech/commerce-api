package io.github.arthurcech.orderscrudcommerce.service.validation.validator;

import io.github.arthurcech.orderscrudcommerce.controller.exception.FieldMessage;
import io.github.arthurcech.orderscrudcommerce.dto.user.RegisterPayload;
import io.github.arthurcech.orderscrudcommerce.repository.UserRepository;
import io.github.arthurcech.orderscrudcommerce.service.validation.annotation.UserInsertValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

import static io.github.arthurcech.orderscrudcommerce.service.constant.ExceptionMessages.USER_ALREADY_EXISTS;

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
        userRepository.findByEmail(payload.email()).ifPresent(user -> fieldsMessage
                .add(new FieldMessage("email", USER_ALREADY_EXISTS)));
        for (FieldMessage f : fieldsMessage) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(f.message())
                    .addPropertyNode(f.fieldName())
                    .addConstraintViolation();
        }
        return fieldsMessage.isEmpty();
    }

}
