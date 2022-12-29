package io.github.arthurcech.orderscrudcommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.arthurcech.orderscrudcommerce.entity.Order;
import io.github.arthurcech.orderscrudcommerce.repository.OrderRepository;
import io.github.arthurcech.orderscrudcommerce.service.exception.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;

	public List<Order> findAll() {
		return repository.findAll();
	}

	public Order findById(Long id) {
		Optional<Order> order = repository.findById(id);
		return order.orElseThrow(() -> new ResourceNotFoundException(id));
	}

}
