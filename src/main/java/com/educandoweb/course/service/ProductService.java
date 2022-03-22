package com.educandoweb.course.service;

import com.educandoweb.course.entity.Product;
import com.educandoweb.course.repository.ProductRepository;
import com.educandoweb.course.service.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new ResourceNotFoundException(id));
    }

}
