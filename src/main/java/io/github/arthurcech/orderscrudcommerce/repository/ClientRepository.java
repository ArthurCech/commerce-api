package io.github.arthurcech.orderscrudcommerce.repository;

import io.github.arthurcech.orderscrudcommerce.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);

}
