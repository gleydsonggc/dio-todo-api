package com.example.demo.controller.exception;

import com.example.demo.service.exception.BusinessException;
import com.example.demo.service.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<String> handleBusinessException(BusinessException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<String> handleNoContentException() {
		return new ResponseEntity<>("Resource ID not found.", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Throwable.class)
	public ResponseEntity<String> handleUnexpectedException(Throwable unexpectedException) {
		String message = "Unexpected server error.";
		log.error(message, unexpectedException);
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}