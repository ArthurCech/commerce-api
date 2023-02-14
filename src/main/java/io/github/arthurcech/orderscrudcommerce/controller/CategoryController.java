package io.github.arthurcech.orderscrudcommerce.controller;

import io.github.arthurcech.orderscrudcommerce.dto.category.CategoryCreatePayload;
import io.github.arthurcech.orderscrudcommerce.dto.category.CategoryResponse;
import io.github.arthurcech.orderscrudcommerce.dto.category.CategoryUpdatePayload;
import io.github.arthurcech.orderscrudcommerce.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Page<CategoryResponse> findAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public CategoryResponse findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> insert(@RequestBody @Valid CategoryCreatePayload payload) {
        CategoryResponse response = categoryService.insert(payload);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public CategoryResponse update(@PathVariable Long id,
                                   @RequestBody @Valid CategoryUpdatePayload payload) {
        return categoryService.update(id, payload);
    }

}
