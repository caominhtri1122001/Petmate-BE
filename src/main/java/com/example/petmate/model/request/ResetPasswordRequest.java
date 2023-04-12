package com.example.petmate.model.request;

import lombok.*;

@Setter
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ResetPasswordRequest {
	private String token;
	private String password;
	private String confirmPassword;
}
