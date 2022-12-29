package io.github.arthurcech.orderscrudcommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.arthurcech.orderscrudcommerce.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
