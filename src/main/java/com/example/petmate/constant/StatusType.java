package com.example.petmate.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusType {
	CANCEL("CANCEL"),
	PENDING("PENDING"),
	ACCEPT("ACCEPT"),
	DONE("DONE");
	private final String type;
}
