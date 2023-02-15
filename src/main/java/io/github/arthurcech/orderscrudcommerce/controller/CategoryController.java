package io.github.arthurcech.orderscrudcommerce.controller;

import io.github.arthurcech.orderscrudcommerce.dto.category.CategoryResponse;
import io.github.arthurcech.orderscrudcommerce.dto.category.CreateCategoryPayload;
import io.github.arthurcech.orderscrudcommerce.dto.category.UpdateCategoryPayload;
import io.github.arthurcech.orderscrudcommerce.service.CategoryService;
import jakarta.validation.Valid;
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
import java.util.List;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<CategoryResponse> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public CategoryResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> insert(
            @RequestBody @Valid CreateCategoryPayload payload
    ) {
        CategoryResponse response = service.insert(payload);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public CategoryResponse update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCategoryPayload payload
    ) {
        return service.update(id, payload);
    }

}
