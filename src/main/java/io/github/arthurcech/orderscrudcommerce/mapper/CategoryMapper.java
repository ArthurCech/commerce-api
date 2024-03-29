package io.github.arthurcech.orderscrudcommerce.mapper;

import io.github.arthurcech.orderscrudcommerce.dto.category.CategoryResponse;
import io.github.arthurcech.orderscrudcommerce.dto.category.CreateCategoryPayload;
import io.github.arthurcech.orderscrudcommerce.dto.category.UpdateCategoryPayload;
import io.github.arthurcech.orderscrudcommerce.entity.Category;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(CreateCategoryPayload payload);

    CategoryResponse toCategoryResponse(Category category);

    void updateCategoryFromPayload(
            UpdateCategoryPayload payload,
            @MappingTarget Category category
    );

}
