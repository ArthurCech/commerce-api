package io.github.arthurcech.orderscrudcommerce.service;

import io.github.arthurcech.orderscrudcommerce.dto.auth.AuthenticationResponse;
import io.github.arthurcech.orderscrudcommerce.dto.user.UpdateUserPayload;
import io.github.arthurcech.orderscrudcommerce.dto.user.UserResponse;
import io.github.arthurcech.orderscrudcommerce.entity.User;
import io.github.arthurcech.orderscrudcommerce.mapper.UserMapper;
import io.github.arthurcech.orderscrudcommerce.repository.UserRepository;
import io.github.arthurcech.orderscrudcommerce.service.exception.DatabaseException;
import io.github.arthurcech.orderscrudcommerce.service.exception.DomainNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.github.arthurcech.orderscrudcommerce.service.constant.ExceptionMessages.USER_NOT_FOUND;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserMapper.INSTANCE::toUserResponse);
    }

    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DomainNotFoundException(USER_NOT_FOUND));
        return UserMapper.INSTANCE.toUserResponse(user);
    }

    @Transactional(readOnly = true)
    public AuthenticationResponse profile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new DomainNotFoundException(USER_NOT_FOUND));
        return UserMapper.INSTANCE.toAuthenticationResponse(user);
    }

    @Transactional
    public UserResponse update(Long id, UpdateUserPayload payload) {
        try {
            User user = userRepository.getReferenceById(id);
            UserMapper.INSTANCE.updateUserFromPayload(payload, user);
            user = userRepository.save(user);
            return UserMapper.INSTANCE.toUserResponse(user);
        } catch (EntityNotFoundException e) {
            throw new DomainNotFoundException(USER_NOT_FOUND);
        }
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DomainNotFoundException(USER_NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
