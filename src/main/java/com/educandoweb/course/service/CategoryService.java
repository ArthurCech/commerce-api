package com.educandoweb.course.service;

import com.educandoweb.course.entity.Category;
import com.educandoweb.course.repository.CategoryRepository;
import com.educandoweb.course.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	public Category findById(Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		return category.orElseThrow(() -> new ResourceNotFoundException(id));
	}

}
