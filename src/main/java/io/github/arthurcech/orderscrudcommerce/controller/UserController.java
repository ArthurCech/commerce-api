package io.github.arthurcech.orderscrudcommerce.controller;

import io.github.arthurcech.orderscrudcommerce.dto.user.UserCreatePayload;
import io.github.arthurcech.orderscrudcommerce.dto.user.UserResponse;
import io.github.arthurcech.orderscrudcommerce.dto.user.UserUpdatePayload;
import io.github.arthurcech.orderscrudcommerce.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(service.findAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> insert(@RequestBody @Valid UserCreatePayload payload) {
        UserResponse response = service.insert(payload);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                               @RequestBody @Valid UserUpdatePayload payload) {
        UserResponse response = service.update(id, payload);
        return ResponseEntity.ok().body(response);
    }

}
