package com.example.petmate.model.request;

import com.example.petmate.constant.ServiceType;
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
public class ServiceRequest {
	private	String name;
	private String description;
	private float price;
	private ServiceType type;
}
