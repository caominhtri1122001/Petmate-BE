package com.example.petmate.mapper;

import com.example.petmate.constant.UserRole;
import com.example.petmate.dto.UserDto;
import com.example.petmate.entity.User;
import com.example.petmate.model.request.AddEmployeeRequest;
import com.example.petmate.model.request.UserRegisterRequest;
import com.example.petmate.model.response.AddEmployeeResponse;
import com.example.petmate.model.response.UserRegisterResponse;
import com.example.petmate.utils.StringUtils;
import com.example.petmate.utils.TimeUtils;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {

	static UserRegisterResponse toUserRegisterResponse(UserRegisterRequest userRegisterRequest) {
		return UserRegisterResponse.builder()
				.firstName(userRegisterRequest.getFirstName())
				.lastName(userRegisterRequest.getLastName())
				.emailAddress(userRegisterRequest.getEmailAddress())
				.dateOfBirth(userRegisterRequest.getDateOfBirth())
				.build();
	}

	static AddEmployeeResponse toAddEmployeeResponse(AddEmployeeRequest addEmployeeRequest) {
		return AddEmployeeResponse.builder()
				.firstName(addEmployeeRequest.getFirstName())
				.lastName(addEmployeeRequest.getLastName())
				.emailAddress(addEmployeeRequest.getEmailAddress())
				.dateOfBirth(addEmployeeRequest.getDateOfBirth())
				.build();
	}

	static User toUserEntity(UserRegisterRequest userRegisterRequest) {
		return User.builder()
				.firstName(userRegisterRequest.getFirstName())
				.lastName(userRegisterRequest.getLastName())
				.email(userRegisterRequest.getEmailAddress())
				.password(StringUtils.getShaStringWithSalt(userRegisterRequest.getPassword(),
						userRegisterRequest.getEmailAddress()))
				.dateOfBirth(TimeUtils.converToLocalDateTimeNoIso(userRegisterRequest.getDateOfBirth()))
				.role(UserRole.CUSTOMER)
				.build();
	}

	static User toEmployeeEntity(AddEmployeeRequest addEmployeeRequest) {
		return User.builder()
				.firstName(addEmployeeRequest.getFirstName())
				.lastName(addEmployeeRequest.getLastName())
				.email(addEmployeeRequest.getEmailAddress())
				.password(StringUtils.getShaStringWithSalt(addEmployeeRequest.getPassword(),
						addEmployeeRequest.getEmailAddress()))
				.dateOfBirth(TimeUtils.converToLocalDateTimeNoIso(addEmployeeRequest.getDateOfBirth()))
				.role(UserRole.EMPLOYEE)
				.build();
	}

	static List<UserDto> toDtoList(List<User> userList) {
		return userList.stream().map(UserMapper::toDto).collect(Collectors.toList());
	}

	static UserDto toDto(User user) {
		return UserDto.builder()
				.lastName(user.getLastName())
				.firstName(user.getFirstName())
				.emailAddress(user.getEmail())
				.image(user.getUserImgUrl())
				.gender(user.isGender())
				.phone(user.getPhone())
				.role(user.getRole().toString())
				.dateOfBirth(TimeUtils.convertToIsoString(user.getDateOfBirth()))
				.build();
	}
}
