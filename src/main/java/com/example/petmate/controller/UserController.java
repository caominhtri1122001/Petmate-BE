package com.example.petmate.controller;

import com.example.petmate.dto.TagDto;
import com.example.petmate.dto.UserDto;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.UserLoginRequest;
import com.example.petmate.model.request.UserRegisterRequest;
import com.example.petmate.model.response.UserLoginResponse;
import com.example.petmate.model.response.UserRegisterResponse;
import com.example.petmate.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Operation(summary = "api to register account")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to register account", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserRegisterRequest.class)) }) })
	@PostMapping("/register")
	public UserRegisterResponse userRegister(UserRegisterRequest registerRequest) throws ResponseException {
		return userService.userRegister(registerRequest);
	}

	@Operation(summary = "api to login account")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to login account", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserLoginRequest.class)) }) })
	@PostMapping("/login")
	public UserLoginResponse userLogin(UserLoginRequest loginRequest) throws ResponseException {
		return userService.userLogin(loginRequest);
	}

	@Operation(summary = "api to forgot password")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to forgot password", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class)) }) })
	@PostMapping("/forgotPassword")
	public void forgotPassword(String email) throws ResponseException {
		userService.forgotPassword(email);
	}

	@Operation(summary = "api to get all user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get all user", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@GetMapping("/getAllUser")
	public List<UserDto> getAllUser() throws ResponseException {
		return userService.getAllUsers();
	}
}
