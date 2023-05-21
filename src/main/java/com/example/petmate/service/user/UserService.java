package com.example.petmate.service.user;

import com.example.petmate.dto.UserDto;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.*;
import com.example.petmate.model.response.AddAdminResponse;
import com.example.petmate.model.response.UserLoginResponse;

import java.util.List;

public interface UserService {
	UserLoginResponse userRegister(UserRegisterRequest request) throws ResponseException;

	AddAdminResponse addAdmin(AddAdminRequest request) throws ResponseException;

	boolean updateAdmin(String id, UpdateAdminRequest request) throws ResponseException;

	boolean deleteAdmin(String id);

	UserLoginResponse userLogin(UserLoginRequest request) throws ResponseException;
	List<UserDto> getAllUsers();

	UserDto getUserById(String id);

	void forgotPassword(String email) throws ResponseException;

	boolean resetPassword(ResetPasswordRequest request) throws ResponseException;

	boolean updateEmployee(String id, UpdateEmployeeRequest request) throws ResponseException;

	UserDto getEmployeeById(String id);

	List<UserDto> getAllEmployee();

	boolean deleteEmployee(String id);

	List<UserDto> getAllCustomer();

	UserDto getCustomerById(String id);

	boolean updateCustomer(String id, UpdateCustomerRequest request) throws ResponseException;

	boolean deleteCustomer(String id);

	boolean resetPasswordToDefault(String id);
}
