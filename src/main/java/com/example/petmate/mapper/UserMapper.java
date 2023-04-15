package com.example.petmate.mapper;

import com.example.petmate.constant.UserRole;
import com.example.petmate.dto.UserDto;
import com.example.petmate.entity.User;
import com.example.petmate.model.request.UserRegisterRequest;
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

	static List<UserDto> toDtoList(List<User> userList) {
		return userList.stream().map(UserMapper::toDto).collect(Collectors.toList());
	}

	static UserDto toDto(User user) {
		return UserDto.builder()
				.name(user.getLastName())
				.build();
	}
}
