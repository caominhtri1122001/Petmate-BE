package com.example.petmate.exception;

import com.example.petmate.constant.ResponseCodes;


public class ResponseException extends RuntimeException {
	private final ResponseCodes responseCode;

	public ResponseException(ResponseCodes responseCode) {
		super(responseCode.getMessage());
		this.responseCode = responseCode;
	}

	public ResponseException(ResponseCodes responseCode, String message) {
		super(message);
		this.responseCode = responseCode;
	}

	public ResponseCodes getResponseCode() {
		return responseCode;
	}
}
