package io.github.arthurcech.orderscrudcommerce.mapper;

import io.github.arthurcech.orderscrudcommerce.dto.product.ProductPayload;
import io.github.arthurcech.orderscrudcommerce.dto.product.ProductResponse;
import io.github.arthurcech.orderscrudcommerce.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductPayload payload);

    ProductResponse toProductResponse(Product product);

    void updateProductFromPayload(ProductPayload payload, @MappingTarget Product product);

}
