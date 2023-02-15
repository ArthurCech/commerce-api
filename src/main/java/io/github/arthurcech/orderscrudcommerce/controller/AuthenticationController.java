package io.github.arthurcech.orderscrudcommerce.controller;

import io.github.arthurcech.orderscrudcommerce.dto.auth.AuthenticationPayload;
import io.github.arthurcech.orderscrudcommerce.dto.auth.AuthenticationResponse;
import io.github.arthurcech.orderscrudcommerce.dto.user.RegisterPayload;
import io.github.arthurcech.orderscrudcommerce.dto.user.ResetPasswordPayload;
import io.github.arthurcech.orderscrudcommerce.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterPayload payload
    ) {
        AuthenticationResponse response = authenticationService.register(payload);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationPayload payload
    ) {
        Map<String, Object> map = authenticationService.authenticate(payload);
        HttpHeaders headers = (HttpHeaders) map.get("accessToken");
        AuthenticationResponse response = (AuthenticationResponse) map.get("user");
        return ResponseEntity.ok().headers(headers).body(response);
    }

    @PostMapping(value = "/reset-password")
    public ResponseEntity<Void> resetPassword(
            @RequestBody @Valid ResetPasswordPayload payload
    ) {
        authenticationService.resetPassword(payload);
        return ResponseEntity.noContent().build();
    }

}
