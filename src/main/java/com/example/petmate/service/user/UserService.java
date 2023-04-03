package com.example.petmate.service.user;

import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.UserLoginRequest;
import com.example.petmate.model.request.UserRegisterRequest;
import com.example.petmate.model.response.UserLoginResponse;
import com.example.petmate.model.response.UserRegisterResponse;

public interface UserService {
	UserRegisterResponse userRegister(UserRegisterRequest request) throws ResponseException;

	UserLoginResponse userLogin(UserLoginRequest request) throws ResponseException;
}
