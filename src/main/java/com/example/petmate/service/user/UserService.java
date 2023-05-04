package com.example.petmate.service.user;

import com.example.petmate.dto.UserDto;
import com.example.petmate.entity.User;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.*;
import com.example.petmate.model.response.AddEmployeeResponse;
import com.example.petmate.model.response.UserLoginResponse;
import com.example.petmate.model.response.UserRegisterResponse;

import java.util.List;

public interface UserService {
	UserRegisterResponse userRegister(UserRegisterRequest request) throws ResponseException;

	AddEmployeeResponse addEmployee(AddEmployeeRequest request) throws ResponseException;

	UserLoginResponse userLogin(UserLoginRequest request) throws ResponseException;
	List<UserDto> getAllUsers();

	void forgotPassword(String email) throws ResponseException;

	boolean resetPassword(ResetPasswordRequest request) throws ResponseException;

	boolean updateEmployee(String id, UpdateEmployeeRequest request) throws ResponseException;

	UserDto getEmployeeById(String id);

	List<UserDto> getAllEmployee();

	boolean deleteEmployee(String id);
}
