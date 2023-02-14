package io.github.arthurcech.orderscrudcommerce.service.validation;

import io.github.arthurcech.orderscrudcommerce.controller.exception.FieldMessage;
import io.github.arthurcech.orderscrudcommerce.dto.category.CategoryCreatePayload;
import io.github.arthurcech.orderscrudcommerce.repository.CategoryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class CategoryInsertValidator implements ConstraintValidator<CategoryInsertValid, CategoryCreatePayload> {

    private final CategoryRepository categoryRepository;

    public CategoryInsertValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void initialize(CategoryInsertValid ann) {
    }

    @Override
    public boolean isValid(CategoryCreatePayload payload, ConstraintValidatorContext context) {
        List<FieldMessage> fieldsMessage = new ArrayList<>();
        categoryRepository.findByName(payload.name()).ifPresent(category -> {
            fieldsMessage.add(new FieldMessage("name", "Category already exists"));
        });
        for (FieldMessage f : fieldsMessage) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(f.getMessage())
                    .addPropertyNode(f.getFieldName())
                    .addConstraintViolation();
        }
        return fieldsMessage.isEmpty();
    }

}
