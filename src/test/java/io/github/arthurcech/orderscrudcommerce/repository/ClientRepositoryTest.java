package io.github.arthurcech.orderscrudcommerce.repository;

import io.github.arthurcech.orderscrudcommerce.entity.Client;
import io.github.arthurcech.orderscrudcommerce.factory.client.ClientFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository repository;

    private long existingId;
    private long nonExistingId;
    private long totalElements;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
        totalElements = 5L;
    }

    @Test
    void saveShouldPersistClientWithAutoIncrementWhenIdIsNull() {
        Client client = ClientFactory.createClient();
        client.setId(null);
        repository.save(client);

        Assertions.assertNotNull(client.getId());
        Assertions.assertEquals(totalElements + 1L, client.getId());
        Assertions.assertEquals("Henry Cyan", client.getName());
        Assertions.assertEquals("henry.cyan@gmail.com", client.getEmail());
        Assertions.assertEquals("5511994113858", client.getPhone());
    }

    @Test
    void saveShouldUpdateClientWhenIdExists() {
        Client client = ClientFactory.createClient();
        repository.save(client);

        Assertions.assertNotNull(client.getId());
        Assertions.assertEquals(existingId, client.getId());
        Assertions.assertEquals("Henry Cyan", client.getName());
        Assertions.assertEquals("henry.cyan@gmail.com", client.getEmail());
        Assertions.assertEquals("5511994113858", client.getPhone());
    }

    @Test
    void deleteShouldDeleteClientWhenIdExists() {
        repository.deleteById(existingId);
        Optional<Client> clientOptional = repository.findById(existingId);

        Assertions.assertFalse(clientOptional.isPresent());
    }

    @Test
    void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });
    }

    @Test
    void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
        Optional<Client> clientOptional = repository.findById(existingId);

        Assertions.assertTrue(clientOptional.isPresent());
    }

    @Test
    void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
        Optional<Client> clientOptional = repository.findById(nonExistingId);

        Assertions.assertFalse(clientOptional.isPresent());
    }

    @Test
    void getReferenceByIdShouldReturnClientWhenIdExists() {
        Client client = repository.getReferenceById(existingId);

        Assertions.assertNotNull(client);
    }

}