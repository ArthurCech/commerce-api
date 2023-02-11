package io.github.arthurcech.orderscrudcommerce.service;

import io.github.arthurcech.orderscrudcommerce.dto.product.CategoryPayload;
import io.github.arthurcech.orderscrudcommerce.dto.product.ProductPayload;
import io.github.arthurcech.orderscrudcommerce.dto.product.ProductResponse;
import io.github.arthurcech.orderscrudcommerce.entity.Category;
import io.github.arthurcech.orderscrudcommerce.entity.Product;
import io.github.arthurcech.orderscrudcommerce.mapper.ProductMapper;
import io.github.arthurcech.orderscrudcommerce.repository.CategoryRepository;
import io.github.arthurcech.orderscrudcommerce.repository.ProductRepository;
import io.github.arthurcech.orderscrudcommerce.service.exception.DatabaseException;
import io.github.arthurcech.orderscrudcommerce.service.exception.DomainNotFoundException;
import io.github.arthurcech.orderscrudcommerce.service.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository repository,
                          CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(Pageable pageable) {
        Page<Product> products = repository.findAll(pageable);
        return products.map(ProductMapper.INSTANCE::toProductResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new DomainNotFoundException("Product not found"));
        return ProductMapper.INSTANCE.toProductResponse(product);
    }

    @Transactional
    public ProductResponse insert(ProductPayload payload) {
        Product product = ProductMapper.INSTANCE.toProduct(payload);
        product.getCategories().clear();
        for (CategoryPayload category : payload.categories()) {
            product.getCategories().add(categoryRepository.getById(category.id()));
        }
        product = repository.save(product);
        return ProductMapper.INSTANCE.toProductResponse(product);
    }

	@Transactional
	public ProductResponse update(Long id, ProductPayload payload) {
		try {
			Product product = repository.getById(id);
            ProductMapper.INSTANCE.updateProductFromPayload(payload, product);
            product.getCategories().clear();
            for (CategoryPayload category : payload.categories()) {
                product.getCategories().add(categoryRepository.getById(category.id()));
            }
            product = repository.save(product);
            return ProductMapper.INSTANCE.toProductResponse(product);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DomainNotFoundException("Product not found");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
