package io.github.arthurcech.orderscrudcommerce.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.github.arthurcech.orderscrudcommerce.service.exception.DatabaseException;
import io.github.arthurcech.orderscrudcommerce.service.exception.ResourceNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> handleResourceNotFound(ResourceNotFoundException exception,
			HttpServletRequest request) {

		String error = "resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;

		StandardError standardError = new StandardError(Instant.now(), status.value(), error, exception.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(status).body(standardError);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> handleDatabaseException(DatabaseException exception,
			HttpServletRequest request) {

		String error = "database error";
		HttpStatus status = HttpStatus.BAD_REQUEST;

		StandardError standardError = new StandardError(Instant.now(), status.value(), error, exception.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(status).body(standardError);
	}

}
