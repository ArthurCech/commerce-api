package io.github.arthurcech.orderscrudcommerce.service.validation.validator;

import io.github.arthurcech.orderscrudcommerce.controller.exception.FieldMessage;
import io.github.arthurcech.orderscrudcommerce.dto.category.CreateCategoryPayload;
import io.github.arthurcech.orderscrudcommerce.repository.CategoryRepository;
import io.github.arthurcech.orderscrudcommerce.service.validation.annotation.CategoryInsertValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

import static io.github.arthurcech.orderscrudcommerce.service.constant.ExceptionMessages.CATEGORY_ALREADY_EXISTS;

public class CategoryInsertValidator implements ConstraintValidator<CategoryInsertValid, CreateCategoryPayload> {

    private final CategoryRepository categoryRepository;

    public CategoryInsertValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void initialize(CategoryInsertValid ann) {
    }

    @Override
    public boolean isValid(
            CreateCategoryPayload payload,
            ConstraintValidatorContext context
    ) {
        List<FieldMessage> fieldsMessage = new ArrayList<>();
        categoryRepository.findByName(payload.name()).ifPresent(category -> fieldsMessage
                .add(new FieldMessage("name", CATEGORY_ALREADY_EXISTS)));
        for (FieldMessage f : fieldsMessage) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(f.message())
                    .addPropertyNode(f.fieldName())
                    .addConstraintViolation();
        }
        return fieldsMessage.isEmpty();
    }

}
