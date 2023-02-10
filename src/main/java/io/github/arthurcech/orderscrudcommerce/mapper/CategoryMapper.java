package io.github.arthurcech.orderscrudcommerce.mapper;

import io.github.arthurcech.orderscrudcommerce.dto.category.CategoryCreatePayload;
import io.github.arthurcech.orderscrudcommerce.dto.category.CategoryResponse;
import io.github.arthurcech.orderscrudcommerce.dto.category.CategoryUpdatePayload;
import io.github.arthurcech.orderscrudcommerce.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(CategoryCreatePayload payload);

    CategoryResponse toCategoryResponse(Category category);

    void updateCategoryFromPayload(CategoryUpdatePayload payload, @MappingTarget Category category);

}
