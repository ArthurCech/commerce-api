package io.github.arthurcech.orderscrudcommerce.service;

import io.github.arthurcech.orderscrudcommerce.dto.category.CategoryPayload;
import io.github.arthurcech.orderscrudcommerce.dto.category.CategoryResponse;
import io.github.arthurcech.orderscrudcommerce.entity.Category;
import io.github.arthurcech.orderscrudcommerce.mapper.CategoryMapper;
import io.github.arthurcech.orderscrudcommerce.repository.CategoryRepository;
import io.github.arthurcech.orderscrudcommerce.service.exception.DatabaseException;
import io.github.arthurcech.orderscrudcommerce.service.exception.DomainNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> findAll(Pageable pageable) {
        Page<Category> categories = repository.findAll(pageable);
        return categories.map(CategoryMapper.INSTANCE::toCategoryResponse);
    }

    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new DomainNotFoundException("Category not found"));
        return CategoryMapper.INSTANCE.toCategoryResponse(category);
    }

    @Transactional
    public CategoryResponse insert(CategoryPayload payload) {
        Category category = CategoryMapper.INSTANCE.toCategory(payload);
        category = repository.save(category);
        return CategoryMapper.INSTANCE.toCategoryResponse(category);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DomainNotFoundException("Category not found");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryPayload payload) {
        try {
            Category category = repository.getById(id);
            CategoryMapper.INSTANCE.updateCategoryFromPayload(payload, category);
            Category updatedCategory = repository.save(category);
            return CategoryMapper.INSTANCE.toCategoryResponse(updatedCategory);
        } catch (EntityNotFoundException e) {
            throw new DomainNotFoundException("Category not found");
        }
    }

}
