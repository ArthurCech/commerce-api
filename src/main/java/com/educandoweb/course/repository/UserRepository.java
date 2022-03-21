package com.educandoweb.course.repository;

import com.educandoweb.course.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
