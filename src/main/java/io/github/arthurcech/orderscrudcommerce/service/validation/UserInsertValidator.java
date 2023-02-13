package io.github.arthurcech.orderscrudcommerce.service.validation;

import io.github.arthurcech.orderscrudcommerce.controller.exception.FieldMessage;
import io.github.arthurcech.orderscrudcommerce.dto.user.UserCreatePayload;
import io.github.arthurcech.orderscrudcommerce.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserCreatePayload> {

    private final UserRepository userRepository;

    public UserInsertValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(UserCreatePayload payload, ConstraintValidatorContext context) {
        List<FieldMessage> fieldsMessage = new ArrayList<>();
        userRepository.findByEmail(payload.email()).ifPresent(user -> {
            fieldsMessage.add(new FieldMessage("email", "Usuário já cadastrado"));
        });
        for (FieldMessage f : fieldsMessage) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(f.getMessage())
                    .addPropertyNode(f.getFieldName())
                    .addConstraintViolation();
        }
        return fieldsMessage.isEmpty();
    }

}
