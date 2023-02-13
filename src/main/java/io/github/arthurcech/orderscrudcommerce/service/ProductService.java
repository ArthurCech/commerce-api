package io.github.arthurcech.orderscrudcommerce.service;

import io.github.arthurcech.orderscrudcommerce.dto.product.ProductCategoryPayload;
import io.github.arthurcech.orderscrudcommerce.dto.product.ProductPayload;
import io.github.arthurcech.orderscrudcommerce.dto.product.ProductResponse;
import io.github.arthurcech.orderscrudcommerce.entity.Product;
import io.github.arthurcech.orderscrudcommerce.mapper.ProductMapper;
import io.github.arthurcech.orderscrudcommerce.repository.CategoryRepository;
import io.github.arthurcech.orderscrudcommerce.repository.ProductRepository;
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
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductMapper.INSTANCE::toProductResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new DomainNotFoundException("Product not found"));
        return ProductMapper.INSTANCE.toProductResponse(product);
    }

    @Transactional
    public ProductResponse insert(ProductPayload payload) {
        Product product = ProductMapper.INSTANCE.toProduct(payload);
        product.getCategories().clear();
        for (ProductCategoryPayload category : payload.categories()) {
            product.getCategories().add(categoryRepository.getById(category.id()));
        }
        product = productRepository.save(product);
        return ProductMapper.INSTANCE.toProductResponse(product);
    }

	@Transactional
	public ProductResponse update(Long id, ProductPayload payload) {
		try {
			Product product = productRepository.getById(id);
            ProductMapper.INSTANCE.updateProductFromPayload(payload, product);
            product.getCategories().clear();
            for (ProductCategoryPayload category : payload.categories()) {
                product.getCategories().add(categoryRepository.getById(category.id()));
            }
            product = productRepository.save(product);
            return ProductMapper.INSTANCE.toProductResponse(product);
		} catch (EntityNotFoundException e) {
			throw new DomainNotFoundException("Product not found");
		}
	}

    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DomainNotFoundException("Product not found");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
