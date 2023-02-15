package io.github.arthurcech.orderscrudcommerce.factory.client;

import io.github.arthurcech.orderscrudcommerce.entity.Client;

public class ClientFactory {

    public static Client createClient() {
        return Client.builder()
                .id(1L)
                .name("Henry Cyan")
                .email("henry.cyan@gmail.com")
                .phone("5511994113858")
                .build();
    }

}
