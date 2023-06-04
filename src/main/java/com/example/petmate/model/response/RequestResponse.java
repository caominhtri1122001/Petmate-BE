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
public class RequestResponse {
	private String requestId;
	private String userId;
	private String sitterId;
	private String sitterName;
	private String sitterAvatar;
	private String petId;
	private String petName;
	private String serviceId;
	private String serviceName;
	private float price;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private String address;
	private String message;
	private boolean status;

}
