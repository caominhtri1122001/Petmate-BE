package com.example.petmate.constant;

public enum ResponseCodes {
	PM_SUCCESS("00", "Success"),
	PM_ERROR_ACCOUNT_NOT_FOUND("01", "Account not found"),
	PM_ERROR_ACCOUNT_ALREADY_EXISTS("02", "Account already exists"),
	PM_ERROR_LOGIN("03", "Invalid email or password"),
	PM_ERROR_REGISTER("04", "Error when register"),
	PM_ERROR_INTERNAL_SERVER("05", "Internal server error"),
	PM_PARAMS_NULL("06", "Params is null"),

	PM_NOT_FOUND("07", "Entity not found"),

	RT_ERROR_VERIFY_FAIL("08", "Token verify failed !"),
	RT_ERROR_SESSION_TIMEOUT("09", "Session timeout!"),

	RT_ERROR_ALREADY_REQUESTED("10", "We already have your request!"),

	RT_ERROR_WRONG_ADDRESS("11", "Your address may be not exsits!"),

	PM_WRONG_OLD_PASSWORD("12", "Wrong old password!!"),

	PM_ALREADY_HAS_SERVICE("13", "You already have this service!!"),
	PM_NOT_AVAILABLE("14", "The requested time conflicts with an existing requests.");
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
