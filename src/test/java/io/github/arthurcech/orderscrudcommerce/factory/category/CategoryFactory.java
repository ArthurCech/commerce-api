package io.github.arthurcech.orderscrudcommerce.factory.category;

import io.github.arthurcech.orderscrudcommerce.entity.Category;

public class CategoryFactory {

    public static Category createCategory() {
        return Category.builder()
                .id(1L)
                .name("Keyboard")
                .build();
    }

}
