package com.example.petmate.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
	private String firstName;

	private String lastName;

	private String emailAddress;

	private String password;

	private String dateOfBirth;
}
