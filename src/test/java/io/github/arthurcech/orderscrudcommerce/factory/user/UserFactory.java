package io.github.arthurcech.orderscrudcommerce.factory.user;

import io.github.arthurcech.orderscrudcommerce.entity.User;
import io.github.arthurcech.orderscrudcommerce.entity.enums.Role;

public class UserFactory {

    public static User createUser() {
        return User.builder()
                .id(1L)
                .name("Jane White")
                .email("jane.white@gmail.com")
                .phone("5511993127910")
                .password("$2a$10$xmiJkeYHCAkiedabYMT5ruacEbIj.d.s2BZcSaVG47H2NyEUpiXzC")
                .role(Role.ROLE_USER)
                .build();
    }

}
