package io.github.arthurcech.orderscrudcommerce.service;

import io.github.arthurcech.orderscrudcommerce.dto.client.ClientResponse;
import io.github.arthurcech.orderscrudcommerce.dto.client.RegisterClientPayload;
import io.github.arthurcech.orderscrudcommerce.dto.client.UpdateClientPayload;
import io.github.arthurcech.orderscrudcommerce.entity.Client;
import io.github.arthurcech.orderscrudcommerce.mapper.ClientMapper;
import io.github.arthurcech.orderscrudcommerce.repository.ClientRepository;
import io.github.arthurcech.orderscrudcommerce.service.exception.DatabaseException;
import io.github.arthurcech.orderscrudcommerce.service.exception.DomainNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.github.arthurcech.orderscrudcommerce.service.constant.ExceptionMessages.CLIENT_NOT_FOUND;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public ClientResponse findById(Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new DomainNotFoundException(CLIENT_NOT_FOUND));
        return ClientMapper.INSTANCE.toClientResponse(client);
    }

    @Transactional
    public ClientResponse insert(RegisterClientPayload payload) {
        Client client = ClientMapper.INSTANCE.toClient(payload);
        repository.save(client);
        return ClientMapper.INSTANCE.toClientResponse(client);
    }

    @Transactional
    public ClientResponse update(Long id, UpdateClientPayload payload) {
        try {
            Client client = repository.getReferenceById(id);
            ClientMapper.INSTANCE.updateClientFromPayload(payload, client);
            repository.save(client);
            return ClientMapper.INSTANCE.toClientResponse(client);
        } catch (EntityNotFoundException e) {
            throw new DomainNotFoundException(CLIENT_NOT_FOUND);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DomainNotFoundException(CLIENT_NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
