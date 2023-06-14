package com.example.petmate.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProviderResponse {
	private String id;
	private String name;
	private String serviceType;
	private float price;
	private String sitterId;
	private boolean disable;
}
