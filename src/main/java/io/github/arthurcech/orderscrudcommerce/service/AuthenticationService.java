package io.github.arthurcech.orderscrudcommerce.service;

import io.github.arthurcech.orderscrudcommerce.config.JwtService;
import io.github.arthurcech.orderscrudcommerce.dto.auth.AuthenticationPayload;
import io.github.arthurcech.orderscrudcommerce.dto.auth.AuthenticationResponse;
import io.github.arthurcech.orderscrudcommerce.dto.user.RegisterPayload;
import io.github.arthurcech.orderscrudcommerce.dto.user.ResetPasswordPayload;
import io.github.arthurcech.orderscrudcommerce.entity.User;
import io.github.arthurcech.orderscrudcommerce.entity.enums.Role;
import io.github.arthurcech.orderscrudcommerce.mapper.UserMapper;
import io.github.arthurcech.orderscrudcommerce.repository.UserRepository;
import io.github.arthurcech.orderscrudcommerce.service.exception.DomainNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static io.github.arthurcech.orderscrudcommerce.service.constant.ExceptionMessages.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterPayload payload) {
        User user = UserMapper.INSTANCE.toUser(payload);
        user.setPassword(passwordEncoder.encode(payload.password()));
        user.setRole(Role.ROLE_USER);
        user = userRepository.save(user);
        return UserMapper.INSTANCE.toAuthenticationResponse(user);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> authenticate(AuthenticationPayload payload) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        payload.email(),
                        payload.password()
                )
        );
        User user = userRepository.findByEmail(payload.email())
                .orElseThrow(() -> new DomainNotFoundException(USER_NOT_FOUND));
        return createResponseWithToken(user);
    }

    @Transactional
    public void resetPassword(ResetPasswordPayload payload) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new DomainNotFoundException(USER_NOT_FOUND));
        user.setPassword(passwordEncoder.encode(payload.password()));
        userRepository.save(user);
    }

    private Map<String, Object> createResponseWithToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        String jwtToken = jwtService.generateToken(claims, user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("access_token", jwtToken);
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", headers);
        map.put("user", UserMapper.INSTANCE.toAuthenticationResponse(user));
        return map;
    }

}
