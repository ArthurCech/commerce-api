package io.github.arthurcech.orderscrudcommerce.controller;

import io.github.arthurcech.orderscrudcommerce.dto.user.UserResponse;
import io.github.arthurcech.orderscrudcommerce.dto.user.UserUpdatePayload;
import io.github.arthurcech.orderscrudcommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserResponse> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public UserResponse update(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdatePayload payload
    ) {
        return userService.update(id, payload);
    }

}
