package com.example.petmate.controller;

import com.example.petmate.dto.TagDto;
import com.example.petmate.dto.UserDto;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.*;
import com.example.petmate.model.response.UserLoginResponse;
import com.example.petmate.model.response.UserRegisterResponse;
import com.example.petmate.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
	public ResponseEntity<UserLoginResponse> userRegister(@RequestBody UserRegisterRequest registerRequest) throws ResponseException {
		return ResponseEntity.ok(userService.userRegister(registerRequest));
	}

	@Operation(summary = "api to login account")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to login account", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserLoginRequest.class)) }) })
	@PostMapping("/login")
	public ResponseEntity<UserLoginResponse> userLogin(@RequestBody UserLoginRequest loginRequest) throws ResponseException {
		return ResponseEntity.ok(userService.userLogin(loginRequest));
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

	@Operation(summary = "api to get user by id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get user by id", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserDto.class)) }) })
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable String id) throws ResponseException {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	@Operation(summary = "api to update profile for user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to update profile for user", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UpdateProfileUserRequest.class)) }) })
	@PatchMapping("/{id}")
	public Boolean updateCustomer(@PathVariable String id, @ModelAttribute UpdateProfileUserRequest request) throws ResponseException, IOException {
		return userService.updateProfileUser(id, request);
	}

	@Operation(summary = "api to change password in profile")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to change password in profile", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ChangePasswordRequest.class)) }) })
	@PostMapping("/changePassword/{id}")
	public Boolean changePassword(@PathVariable String id, @RequestBody ChangePasswordRequest request) throws ResponseException {
		 return userService.changePassword(id, request);
	}
}
