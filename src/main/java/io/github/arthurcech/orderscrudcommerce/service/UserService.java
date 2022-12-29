package io.github.arthurcech.orderscrudcommerce.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.arthurcech.orderscrudcommerce.dto.UserDTO;
import io.github.arthurcech.orderscrudcommerce.entity.User;
import io.github.arthurcech.orderscrudcommerce.repository.UserRepository;
import io.github.arthurcech.orderscrudcommerce.service.exception.DatabaseException;
import io.github.arthurcech.orderscrudcommerce.service.exception.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public Page<UserDTO> findAll(Pageable pageable) {
		Page<User> users = repository.findAll(pageable);
		return users.map(user -> new UserDTO(user));
	}

	public UserDTO findById(Long id) {
		User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		return new UserDTO(user);
	}

	public UserDTO insert(UserDTO dto) {
		User user = new User();
		dtoToUser(dto, user);
		user = repository.save(user);
		return new UserDTO(user);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public UserDTO update(Long id, UserDTO dto) {
		try {
			User user = repository.getById(id);
			dtoToUser(dto, user);
			user = repository.save(user);
			return new UserDTO(user);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void dtoToUser(UserDTO dto, User user) {
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPhone(dto.getPhone());
		user.setPassword(dto.getPassword());
	}

}
