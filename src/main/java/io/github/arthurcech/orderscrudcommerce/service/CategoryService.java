package io.github.arthurcech.orderscrudcommerce.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.arthurcech.orderscrudcommerce.dto.CategoryDTO;
import io.github.arthurcech.orderscrudcommerce.entity.Category;
import io.github.arthurcech.orderscrudcommerce.repository.CategoryRepository;
import io.github.arthurcech.orderscrudcommerce.service.exception.DatabaseException;
import io.github.arthurcech.orderscrudcommerce.service.exception.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	public Page<CategoryDTO> findAll(Pageable pageable) {
		Page<Category> categories = repository.findAll(pageable);
		return categories.map(category -> new CategoryDTO(category));
	}

	public CategoryDTO findById(Long id) {
		Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		return new CategoryDTO(category);
	}

	public CategoryDTO insert(CategoryDTO dto) {
		Category category = new Category();
		dtoToCategory(dto, category);
		category = repository.save(category);
		return new CategoryDTO(category);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category category = repository.getById(id);
			dtoToCategory(dto, category);
			category = repository.save(category);
			return new CategoryDTO(category);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void dtoToCategory(CategoryDTO dto, Category category) {
		category.setName(dto.getName());
	}

}
