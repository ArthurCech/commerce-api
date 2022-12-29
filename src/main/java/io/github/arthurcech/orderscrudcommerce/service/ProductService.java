package io.github.arthurcech.orderscrudcommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.arthurcech.orderscrudcommerce.entity.Product;
import io.github.arthurcech.orderscrudcommerce.repository.ProductRepository;
import io.github.arthurcech.orderscrudcommerce.service.exception.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public List<Product> findAll() {
		return repository.findAll();
	}

	public Product findById(Long id) {
		Optional<Product> product = repository.findById(id);
		return product.orElseThrow(() -> new ResourceNotFoundException(id));
	}

}
