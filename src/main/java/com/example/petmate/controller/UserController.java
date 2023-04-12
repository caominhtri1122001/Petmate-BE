package com.example.petmate.controller;

import com.example.petmate.dto.UserDto;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.UserLoginRequest;
import com.example.petmate.model.request.UserRegisterRequest;
import com.example.petmate.model.response.UserLoginResponse;
import com.example.petmate.model.response.UserRegisterResponse;
import com.example.petmate.service.user.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value ="/userRegister", method = RequestMethod.POST)
	public UserRegisterResponse userRegister(UserRegisterRequest registerRequest) throws ResponseException {
		return userService.userRegister(registerRequest);
	}

	@RequestMapping(value ="/userLogin", method = RequestMethod.POST)
	public UserLoginResponse userLogin(UserLoginRequest loginRequest) throws ResponseException {
		return userService.userLogin(loginRequest);
	}

	@RequestMapping(value ="/forgotPassword", method = RequestMethod.POST)
	public void forgotPassword(String email) throws ResponseException {
		userService.forgotPassword(email);
	}

	@RequestMapping(value ="/getAllUser", method = RequestMethod.GET)
	public List<UserDto> getAllUser() throws ResponseException {
		return userService.getAllUsers();
	}
}
