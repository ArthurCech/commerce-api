package io.github.arthurcech.orderscrudcommerce.service;

import io.github.arthurcech.orderscrudcommerce.dto.category.CategoryCreatePayload;
import io.github.arthurcech.orderscrudcommerce.dto.category.CategoryResponse;
import io.github.arthurcech.orderscrudcommerce.dto.category.CategoryUpdatePayload;
import io.github.arthurcech.orderscrudcommerce.entity.Category;
import io.github.arthurcech.orderscrudcommerce.mapper.CategoryMapper;
import io.github.arthurcech.orderscrudcommerce.repository.CategoryRepository;
import io.github.arthurcech.orderscrudcommerce.service.exception.DatabaseException;
import io.github.arthurcech.orderscrudcommerce.service.exception.DomainNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> findAll(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(CategoryMapper.INSTANCE::toCategoryResponse);
    }

    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new DomainNotFoundException("Category not found"));
        return CategoryMapper.INSTANCE.toCategoryResponse(category);
    }

    @Transactional
    public CategoryResponse insert(CategoryCreatePayload payload) {
        Category category = CategoryMapper.INSTANCE.toCategory(payload);
        category = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.toCategoryResponse(category);
    }

    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DomainNotFoundException("Category not found");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryUpdatePayload payload) {
        try {
            Category category = categoryRepository.getById(id);
            CategoryMapper.INSTANCE.updateCategoryFromPayload(payload, category);
            category = categoryRepository.save(category);
            return CategoryMapper.INSTANCE.toCategoryResponse(category);
        } catch (EntityNotFoundException e) {
            throw new DomainNotFoundException("Category not found");
        }
    }

}
