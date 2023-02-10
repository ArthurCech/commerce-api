package io.github.arthurcech.orderscrudcommerce.repository;

import io.github.arthurcech.orderscrudcommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
