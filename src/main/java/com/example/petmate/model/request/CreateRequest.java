package com.example.petmate.model.request;

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
public class CreateRequest {
	private String userId;
	private String sitterId;
	private String petId;
	private String serviceId;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private String message;
	private String address;
}
