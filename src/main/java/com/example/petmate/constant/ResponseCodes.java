package com.example.petmate.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public enum ResponseCodes {
	PM_SUCCESS("00", "Success"),
	PM_ERROR_ACCOUNT_NOT_FOUND("01", "Account not found"),
	PM_ERROR_ACCOUNT_ALREADY_EXISTS("02", "Account already exists"),
	PM_ERROR_LOGIN("03", "Invalid email or password"),
	PM_ERROR_REGISTER("04", "Error when register"),
	PM_ERROR_INTERNAL_SERVER("05", "Internal server error");
	private String code;
	private String message;

	ResponseCodes(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
