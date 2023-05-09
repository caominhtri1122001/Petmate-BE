package com.example.petmate.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceType {
	VIP("VIP"),
	NORMAL("NORMAL");

	private final String type;
}
