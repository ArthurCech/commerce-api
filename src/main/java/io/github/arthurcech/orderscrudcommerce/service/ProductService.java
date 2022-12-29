package io.github.arthurcech.orderscrudcommerce.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.arthurcech.orderscrudcommerce.dto.CategoryDTO;
import io.github.arthurcech.orderscrudcommerce.dto.ProductDTO;
import io.github.arthurcech.orderscrudcommerce.entity.Category;
import io.github.arthurcech.orderscrudcommerce.entity.Product;
import io.github.arthurcech.orderscrudcommerce.repository.CategoryRepository;
import io.github.arthurcech.orderscrudcommerce.repository.ProductRepository;
import io.github.arthurcech.orderscrudcommerce.service.exception.DatabaseException;
import io.github.arthurcech.orderscrudcommerce.service.exception.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	@Autowired
	private CategoryRepository categoryRepository;

	public Page<ProductDTO> findAll(Pageable pageable) {
		Page<Product> products = repository.findAll(pageable);
		return products.map(product -> new ProductDTO(product));
	}

	public ProductDTO findById(Long id) {
		Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		return new ProductDTO(product, product.getCategories());
	}

	public ProductDTO insert(ProductDTO dto) {
		Product product = new Product();
		dtoToProduct(dto, product);
		product = repository.save(product);
		return new ProductDTO(product, product.getCategories());
	}

	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product product = repository.getById(id);
			dtoToProduct(dto, product);
			product = repository.save(product);
			return new ProductDTO(product, product.getCategories());
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
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

	private void dtoToProduct(ProductDTO dto, Product product) {
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setImgUrl(dto.getImgUrl());

		product.getCategories().clear();
		for (CategoryDTO catDto : dto.getCategories()) {
			Category category = categoryRepository.getById(catDto.getId());
			product.getCategories().add(category);
		}
	}

}
