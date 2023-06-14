package com.example.petmate.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceType {
	PER_SERVING("PER SERVING"),
	PET_DAY("PER DAY");

	private final String type;
}
