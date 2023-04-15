package com.example.petmate.exception;

import com.example.petmate.constant.ResponseCodes;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(ResponseExceptionHandler.class);

	@ExceptionHandler(ResponseException.class)
	public ResponseEntity<ErrorResponse> handleResponseException(ResponseException e) {
		ErrorResponse errorResponse = new ErrorResponse(e.getResponseCode().toString(), e.getMessage());
		logger.error("ResponseException: {}", errorResponse);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
		ErrorResponse errorResponse = new ErrorResponse(ResponseCodes.PM_ERROR_INTERNAL_SERVER.name(), e.getMessage());
		logger.error("Exception: {}", errorResponse, e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}


}
