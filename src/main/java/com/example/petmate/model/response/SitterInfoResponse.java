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
public class SitterInfoResponse {
	private String userId;
	private String firstname;
	private String lastName;
	private String userImage;
	private String phone;
	private String address;
	private double latitude;
	private double longitude;
	private int yearOfExperience;
	private String description;
}
