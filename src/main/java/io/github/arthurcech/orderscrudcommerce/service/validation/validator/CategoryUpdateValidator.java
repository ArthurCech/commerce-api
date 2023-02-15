package io.github.arthurcech.orderscrudcommerce.service.validation.validator;

import io.github.arthurcech.orderscrudcommerce.controller.exception.FieldMessage;
import io.github.arthurcech.orderscrudcommerce.dto.category.UpdateCategoryPayload;
import io.github.arthurcech.orderscrudcommerce.entity.Category;
import io.github.arthurcech.orderscrudcommerce.repository.CategoryRepository;
import io.github.arthurcech.orderscrudcommerce.service.validation.annotation.CategoryUpdateValid;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.github.arthurcech.orderscrudcommerce.service.constant.ExceptionMessages.CATEGORY_ALREADY_EXISTS;

public class CategoryUpdateValidator implements ConstraintValidator<CategoryUpdateValid, UpdateCategoryPayload> {

    private final HttpServletRequest request;
    private final CategoryRepository categoryRepository;

    public CategoryUpdateValidator(HttpServletRequest request,
                                   CategoryRepository categoryRepository) {
        this.request = request;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void initialize(CategoryUpdateValid ann) {
    }

    @Override
    public boolean isValid(
            UpdateCategoryPayload payload,
            ConstraintValidatorContext context
    ) {
        @SuppressWarnings("unchecked")
        Map<String, String> uriVars = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long categoryId = Long.parseLong(uriVars.get("id"));
        List<FieldMessage> fieldsMessage = new ArrayList<>();
        Optional<Category> categoryOptional = categoryRepository.findByName(payload.name());
        if (categoryOptional.isPresent() && categoryId != categoryOptional.get().getId()) {
            fieldsMessage.add(new FieldMessage("name", CATEGORY_ALREADY_EXISTS));
        }
        for (FieldMessage f : fieldsMessage) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(f.message())
                    .addPropertyNode(f.fieldName())
                    .addConstraintViolation();
        }
        return fieldsMessage.isEmpty();
    }

}
