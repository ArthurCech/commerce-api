package io.github.arthurcech.orderscrudcommerce.repository;

import io.github.arthurcech.orderscrudcommerce.entity.User;
import io.github.arthurcech.orderscrudcommerce.entity.enums.Role;
import io.github.arthurcech.orderscrudcommerce.factory.user.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    private long existingId;
    private long nonExistingId;
    private long totalElements;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
        totalElements = 2L;
    }

    @Test
    void saveShouldPersistUserWithAutoIncrementWhenIdIsNull() {
        User user = UserFactory.createUser();
        user.setId(null);
        repository.save(user);

        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(totalElements + 1L, user.getId());
        Assertions.assertEquals("Jane White", user.getName());
        Assertions.assertEquals("jane.white@gmail.com", user.getEmail());
        Assertions.assertEquals("5511993127910", user.getPhone());
        Assertions.assertEquals("$2a$10$xmiJkeYHCAkiedabYMT5ruacEbIj.d.s2BZcSaVG47H2NyEUpiXzC", user.getPassword());
        Assertions.assertEquals(Role.ROLE_USER, user.getRole());
    }

    @Test
    void saveShouldUpdateUserWhenIdExists() {
        User user = UserFactory.createUser();
        repository.save(user);

        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(existingId, user.getId());
        Assertions.assertEquals("Jane White", user.getName());
        Assertions.assertEquals("jane.white@gmail.com", user.getEmail());
        Assertions.assertEquals("5511993127910", user.getPhone());
        Assertions.assertEquals("$2a$10$xmiJkeYHCAkiedabYMT5ruacEbIj.d.s2BZcSaVG47H2NyEUpiXzC", user.getPassword());
        Assertions.assertEquals(Role.ROLE_USER, user.getRole());
    }

    @Test
    void deleteShouldDeleteUserWhenIdExists() {
        repository.deleteById(existingId);
        Optional<User> userOptional = repository.findById(existingId);

        Assertions.assertFalse(userOptional.isPresent());
    }

    @Test
    void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });
    }

    @Test
    void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
        Optional<User> userOptional = repository.findById(existingId);

        Assertions.assertTrue(userOptional.isPresent());
    }

    @Test
    void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
        Optional<User> userOptional = repository.findById(nonExistingId);

        Assertions.assertFalse(userOptional.isPresent());
    }

    @Test
    void getReferenceByIdShouldReturnUserWhenIdExists() {
        User user = repository.getReferenceById(existingId);

        Assertions.assertNotNull(user);
    }

    @Test
    void findAllShouldReturnNonEmptyList() {
        Page<User> page = repository.findAll(PageRequest.of(0, 10));

        Assertions.assertNotNull(page);
//        Assertions.assertEquals("Maria Brown", page.getContent().get(0).getName());
//        Assertions.assertEquals("maria.brown@gmail.com", page.getContent().get(0).getEmail());
//        Assertions.assertEquals("988888888", page.getContent().get(0).getPhone());
//        Assertions.assertEquals("$2a$10$xmiJkeYHCAkiedabYMT5ruacEbIj.d.s2BZcSaVG47H2NyEUpiXzC", page.getContent().get(0).getPassword());
//        Assertions.assertEquals(Role.ROLE_ADMIN, page.getContent().get(0).getRole());
        Assertions.assertEquals(totalElements, page.getTotalElements());
    }

}