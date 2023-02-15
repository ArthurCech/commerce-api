package io.github.arthurcech.orderscrudcommerce.repository;

import io.github.arthurcech.orderscrudcommerce.entity.Product;
import io.github.arthurcech.orderscrudcommerce.factory.product.ProductFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

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
    void saveShouldPersistProductWithAutoIncrementWhenIdIsNull() {
        Product product = ProductFactory.createProduct();
        product.setId(null);
        repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(totalElements + 1L, product.getId());
        Assertions.assertEquals("Teclado Mecânico Gamer Logitech G613", product.getName());
        Assertions.assertEquals("Sem Fio LIGHTSPEED", product.getDescription());
        Assertions.assertEquals(BigDecimal.valueOf(699.90), product.getPrice());
        Assertions.assertEquals("shorturl.at/alE28", product.getImgUrl());
        Assertions.assertEquals("shorturl.at/alE28", product.getImgUrl());
        Assertions.assertEquals(1, product.getCategories().size());
    }

    @Test
    void saveShouldUpdateProductWhenIdExists() {
        Product product = ProductFactory.createProduct();
        repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(existingId, product.getId());
        Assertions.assertEquals("Teclado Mecânico Gamer Logitech G613", product.getName());
        Assertions.assertEquals("Sem Fio LIGHTSPEED", product.getDescription());
        Assertions.assertEquals(BigDecimal.valueOf(699.90), product.getPrice());
        Assertions.assertEquals("shorturl.at/alE28", product.getImgUrl());
        Assertions.assertEquals("shorturl.at/alE28", product.getImgUrl());
        Assertions.assertEquals(1, product.getCategories().size());
    }

    @Test
    void deleteShouldDeleteProductWhenIdExists() {
        repository.deleteById(existingId);
        Optional<Product> productOptional = repository.findById(existingId);

        Assertions.assertFalse(productOptional.isPresent());
    }

    @Test
    void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });
    }

    @Test
    void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
        Optional<Product> productOptional = repository.findById(existingId);

        Assertions.assertTrue(productOptional.isPresent());
    }

    @Test
    void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
        Optional<Product> productOptional = repository.findById(nonExistingId);

        Assertions.assertFalse(productOptional.isPresent());
    }

    @Test
    void getReferenceByIdShouldReturnProductWhenIdExists() {
        Product product = repository.getReferenceById(existingId);

        Assertions.assertNotNull(product);
    }

    @Test
    void findAllShouldReturnNonEmptyPage() {
        Page<Product> page = repository.findAll(PageRequest.of(0, 10));

        Assertions.assertNotNull(page);
        Assertions.assertEquals("The Lord of the Rings", page.getContent().get(0).getName());
        Assertions.assertEquals(totalElements, page.getTotalElements());
    }

}