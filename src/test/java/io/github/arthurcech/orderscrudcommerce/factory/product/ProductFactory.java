package io.github.arthurcech.orderscrudcommerce.factory.product;

import io.github.arthurcech.orderscrudcommerce.entity.Product;
import io.github.arthurcech.orderscrudcommerce.factory.category.CategoryFactory;

import java.math.BigDecimal;
import java.util.Set;

public class ProductFactory {

    public static Product createProduct() {
        return Product.builder()
                .id(1L)
                .name("Teclado Mecânico Gamer Logitech G613")
                .description("Sem Fio LIGHTSPEED")
                .price(BigDecimal.valueOf(699.90))
                .imgUrl("shorturl.at/alE28")
                .categories(Set.of(CategoryFactory.createCategory()))
                .build();
    }

}
