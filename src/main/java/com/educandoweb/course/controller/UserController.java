package com.educandoweb.course.controller;

import com.educandoweb.course.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @GetMapping
    public ResponseEntity<User> findAll() {
        User u = new User(1L, "Arthur", "arthur@gmail.com", "1234-5678", "123456");
        return ResponseEntity.ok().body(u);
    }

}
