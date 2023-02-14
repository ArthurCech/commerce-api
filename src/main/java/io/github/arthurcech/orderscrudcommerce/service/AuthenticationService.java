package io.github.arthurcech.orderscrudcommerce.service;

import io.github.arthurcech.orderscrudcommerce.config.JwtService;
import io.github.arthurcech.orderscrudcommerce.dto.auth.AuthenticationPayload;
import io.github.arthurcech.orderscrudcommerce.dto.user.RegisterPayload;
import io.github.arthurcech.orderscrudcommerce.entity.User;
import io.github.arthurcech.orderscrudcommerce.entity.enums.Role;
import io.github.arthurcech.orderscrudcommerce.mapper.UserMapper;
import io.github.arthurcech.orderscrudcommerce.repository.UserRepository;
import io.github.arthurcech.orderscrudcommerce.service.exception.DomainNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Map<String, Object> register(RegisterPayload payload) {
        User user = UserMapper.INSTANCE.toUser(payload);
        user.setPassword(passwordEncoder.encode(payload.password()));
        user.setRole(Role.USER);
        user = userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("access_token", jwtToken);

        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", httpHeaders);
        map.put("user", UserMapper.INSTANCE.toAuthenticationResponse(user));
        return map;
    }

    public Map<String, Object> authenticate(AuthenticationPayload payload) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        payload.email(),
                        payload.password()
                )
        );
        User user = userRepository.findByEmail(payload.email())
                .orElseThrow(() -> new DomainNotFoundException("User not found"));

        String jwtToken = jwtService.generateToken(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("access_token", jwtToken);

        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", headers);
        map.put("user", UserMapper.INSTANCE.toAuthenticationResponse(user));
        return map;
    }

}
