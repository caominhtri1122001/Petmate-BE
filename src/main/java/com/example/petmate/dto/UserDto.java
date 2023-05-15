package com.example.petmate.dto;

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
public class UserDto {
	private String firstName;

	private String lastName;

	private String image;

	private String role;

	private boolean gender;

	private String phone;

	private String emailAddress;

	private String dateOfBirth;
}
