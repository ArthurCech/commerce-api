package io.github.arthurcech.orderscrudcommerce.repository;

import io.github.arthurcech.orderscrudcommerce.entity.Category;
import io.github.arthurcech.orderscrudcommerce.factory.category.CategoryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    private long existingId;
    private long nonExistingId;
    private long totalElements;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
        totalElements = 3L;
    }

    @Test
    void saveShouldPersistCategoryWithAutoIncrementWhenIdIsNull() {
        Category category = CategoryFactory.createCategory();
        category.setId(null);
        repository.save(category);

        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(totalElements + 1L, category.getId());
        Assertions.assertEquals("Keyboard", category.getName());
    }

    @Test
    void saveShouldUpdateCategoryWhenIdExists() {
        Category category = CategoryFactory.createCategory();
        repository.save(category);

        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(existingId, category.getId());
        Assertions.assertEquals("Keyboard", category.getName());
    }

    @Test
    void deleteShouldDeleteCategoryWhenIdExists() {
        repository.deleteById(existingId);
        Optional<Category> categoryOptional = repository.findById(existingId);

        Assertions.assertFalse(categoryOptional.isPresent());
    }

    @Test
    void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });
    }

    @Test
    void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
        Optional<Category> categoryOptional = repository.findById(existingId);

        Assertions.assertTrue(categoryOptional.isPresent());
    }

    @Test
    void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
        Optional<Category> categoryOptional = repository.findById(nonExistingId);

        Assertions.assertFalse(categoryOptional.isPresent());
    }

    @Test
    void getReferenceByIdShouldReturnCategoryWhenIdExists() {
        Category category = repository.getReferenceById(existingId);

        Assertions.assertNotNull(category);
    }

    @Test
    void findAllShouldReturnNonEmptyList() {
        List<Category> list = repository.findAll(Sort.by("name"));

        Assertions.assertNotNull(list);
        Assertions.assertEquals("Book", list.get(0).getName());
        Assertions.assertEquals(totalElements, list.size());
    }

}