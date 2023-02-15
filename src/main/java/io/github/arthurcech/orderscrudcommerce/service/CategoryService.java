package io.github.arthurcech.orderscrudcommerce.service;

import io.github.arthurcech.orderscrudcommerce.dto.category.CategoryResponse;
import io.github.arthurcech.orderscrudcommerce.dto.category.CreateCategoryPayload;
import io.github.arthurcech.orderscrudcommerce.dto.category.UpdateCategoryPayload;
import io.github.arthurcech.orderscrudcommerce.entity.Category;
import io.github.arthurcech.orderscrudcommerce.mapper.CategoryMapper;
import io.github.arthurcech.orderscrudcommerce.repository.CategoryRepository;
import io.github.arthurcech.orderscrudcommerce.service.exception.DatabaseException;
import io.github.arthurcech.orderscrudcommerce.service.exception.DomainNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.github.arthurcech.orderscrudcommerce.service.constant.ExceptionMessages.CATEGORY_NOT_FOUND;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        List<Category> list = categoryRepository.findAll();
        return list.stream()
                .map(CategoryMapper.INSTANCE::toCategoryResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new DomainNotFoundException(CATEGORY_NOT_FOUND));
        return CategoryMapper.INSTANCE.toCategoryResponse(category);
    }

    @Transactional
    public CategoryResponse insert(CreateCategoryPayload payload) {
        Category category = CategoryMapper.INSTANCE.toCategory(payload);
        category = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.toCategoryResponse(category);
    }

    @Transactional
    public CategoryResponse update(Long id, UpdateCategoryPayload payload) {
        try {
            Category category = categoryRepository.getReferenceById(id);
            CategoryMapper.INSTANCE.updateCategoryFromPayload(payload, category);
            category = categoryRepository.save(category);
            return CategoryMapper.INSTANCE.toCategoryResponse(category);
        } catch (EntityNotFoundException e) {
            throw new DomainNotFoundException(CATEGORY_NOT_FOUND);
        }
    }

    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DomainNotFoundException(CATEGORY_NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
