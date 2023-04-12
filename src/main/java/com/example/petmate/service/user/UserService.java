package com.example.petmate.service.user;

import com.example.petmate.dto.UserDto;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.ResetPasswordRequest;
import com.example.petmate.model.request.UserLoginRequest;
import com.example.petmate.model.request.UserRegisterRequest;
import com.example.petmate.model.response.UserLoginResponse;
import com.example.petmate.model.response.UserRegisterResponse;

import java.util.List;

public interface UserService {
	UserRegisterResponse userRegister(UserRegisterRequest request) throws ResponseException;

	UserLoginResponse userLogin(UserLoginRequest request) throws ResponseException;

	List<UserDto> getAllUsers();

	void forgotPassword(String email) throws ResponseException;

	boolean resetPassword(ResetPasswordRequest request) throws ResponseException;
}
