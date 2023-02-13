package io.github.arthurcech.orderscrudcommerce.service.validation;

import io.github.arthurcech.orderscrudcommerce.controller.exception.FieldMessage;
import io.github.arthurcech.orderscrudcommerce.dto.category.CategoryUpdatePayload;
import io.github.arthurcech.orderscrudcommerce.entity.Category;
import io.github.arthurcech.orderscrudcommerce.repository.CategoryRepository;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CategoryUpdateValidator implements ConstraintValidator<CategoryUpdateValid, CategoryUpdatePayload> {

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
    public boolean isValid(CategoryUpdatePayload payload, ConstraintValidatorContext context) {
        @SuppressWarnings("unchecked")
        Map<String, String> uriVars = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        long categoryId = Long.parseLong(uriVars.get("id"));
        List<FieldMessage> fieldsMessage = new ArrayList<>();
        Optional<Category> categoryOptional = categoryRepository.findByName(payload.name());
        if (categoryOptional.isPresent() && categoryId != categoryOptional.get().getId()) {
            fieldsMessage.add(new FieldMessage("name", "Category already exists"));
        }
        for (FieldMessage f : fieldsMessage) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(f.getMessage())
                    .addPropertyNode(f.getFieldName())
                    .addConstraintViolation();
        }
        return fieldsMessage.isEmpty();
    }

}
