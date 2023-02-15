package io.github.arthurcech.orderscrudcommerce.service.validation.validator;

import io.github.arthurcech.orderscrudcommerce.controller.exception.FieldMessage;
import io.github.arthurcech.orderscrudcommerce.dto.client.RegisterClientPayload;
import io.github.arthurcech.orderscrudcommerce.repository.ClientRepository;
import io.github.arthurcech.orderscrudcommerce.service.validation.annotation.ClientInsertValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

import static io.github.arthurcech.orderscrudcommerce.service.constant.ExceptionMessages.CLIENT_ALREADY_EXISTS;

public class ClientInsertValidator implements ConstraintValidator<ClientInsertValid, RegisterClientPayload> {

    private final ClientRepository clientRepository;

    public ClientInsertValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void initialize(ClientInsertValid ann) {
    }

    @Override
    public boolean isValid(
            RegisterClientPayload payload,
            ConstraintValidatorContext context
    ) {
        List<FieldMessage> fieldsMessage = new ArrayList<>();
        clientRepository.findByEmail(payload.email()).ifPresent(client -> fieldsMessage
                .add(new FieldMessage("email", CLIENT_ALREADY_EXISTS)));
        for (FieldMessage f : fieldsMessage) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(f.message())
                    .addPropertyNode(f.fieldName())
                    .addConstraintViolation();
        }
        return fieldsMessage.isEmpty();
    }

}
