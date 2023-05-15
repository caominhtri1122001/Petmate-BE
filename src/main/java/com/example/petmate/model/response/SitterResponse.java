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
public class SitterResponse {
	private String requestId;
	private String userId;
	private String firstname;
	private String lastName;
	private boolean gender;
	private String dateOfBirth;
	private String phone;
	private String address;
	private String city;
	private String postCode;
	private int yearOfExperience;
	private boolean status;
	private String description;
}
