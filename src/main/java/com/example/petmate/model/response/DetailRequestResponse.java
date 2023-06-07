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
public class DetailRequestResponse {
	private String serviceName;
	private float price;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private String address;
	private String message;
}
