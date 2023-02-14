package io.github.arthurcech.orderscrudcommerce.service.validation;

import io.github.arthurcech.orderscrudcommerce.controller.exception.FieldMessage;
import io.github.arthurcech.orderscrudcommerce.dto.client.UpdateClientPayload;
import io.github.arthurcech.orderscrudcommerce.entity.Client;
import io.github.arthurcech.orderscrudcommerce.repository.ClientRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClientUpdateValidator implements ConstraintValidator<ClientUpdateValid, UpdateClientPayload> {

    private final HttpServletRequest request;
    private final ClientRepository clientRepository;

    public ClientUpdateValidator(HttpServletRequest request,
                                 ClientRepository clientRepository) {
        this.request = request;
        this.clientRepository = clientRepository;
    }

    @Override
    public void initialize(ClientUpdateValid ann) {
    }

    @Override
    public boolean isValid(
            UpdateClientPayload payload,
            ConstraintValidatorContext context
    ) {
        @SuppressWarnings("unchecked")
        Map<String, String> uriVars = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long clientId = Long.parseLong(uriVars.get("id"));
        List<FieldMessage> fieldsMessage = new ArrayList<>();
        Optional<Client> clientOptional = clientRepository.findByEmail(payload.email());
        if (clientOptional.isPresent() && clientId != clientOptional.get().getId()) {
            fieldsMessage.add(new FieldMessage("email", "Client already exists"));
        }
        for (FieldMessage f : fieldsMessage) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(f.message())
                    .addPropertyNode(f.fieldName())
                    .addConstraintViolation();
        }
        return fieldsMessage.isEmpty();
    }

}
