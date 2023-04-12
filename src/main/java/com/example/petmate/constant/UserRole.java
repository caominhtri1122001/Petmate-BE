package com.example.petmate.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
	ADMIN("ADMIN"),
	CUSTOMER("CUSTOMER"),

	EMPLOYEE("EMPLOYEE");
	private final String type;
}
