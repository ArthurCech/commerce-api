package io.github.arthurcech.orderscrudcommerce.service.validation.validator;

import io.github.arthurcech.orderscrudcommerce.controller.exception.FieldMessage;
import io.github.arthurcech.orderscrudcommerce.dto.client.RegisterClientPayload;
import io.github.arthurcech.orderscrudcommerce.repository.ClientRepository;
import io.github.arthurcech.orderscrudcommerce.service.validation.annotation.ClientInsertValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

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
        clientRepository.findByEmail(payload.email()).ifPresent(client -> {
            fieldsMessage.add(new FieldMessage("email", "Client already exists"));
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
