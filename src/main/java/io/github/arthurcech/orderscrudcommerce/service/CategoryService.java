package io.github.arthurcech.orderscrudcommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.arthurcech.orderscrudcommerce.entity.Category;
import io.github.arthurcech.orderscrudcommerce.repository.CategoryRepository;
import io.github.arthurcech.orderscrudcommerce.service.exception.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	public List<Category> findAll() {
		return repository.findAll();
	}

	public Category findById(Long id) {
		Optional<Category> category = repository.findById(id);
		return category.orElseThrow(() -> new ResourceNotFoundException(id));
	}

}
