package io.github.arthurcech.orderscrudcommerce.repository;

import io.github.arthurcech.orderscrudcommerce.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
