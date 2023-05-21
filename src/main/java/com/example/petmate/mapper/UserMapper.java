package com.example.petmate.mapper;

import com.example.petmate.constant.UserRole;
import com.example.petmate.dto.UserDto;
import com.example.petmate.entity.User;
import com.example.petmate.model.request.AddAdminRequest;
import com.example.petmate.model.request.UserRegisterRequest;
import com.example.petmate.model.response.AddAdminResponse;
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

	static AddAdminResponse toAddAdminResponse(AddAdminRequest addAdminRequest) {
		return AddAdminResponse.builder()
				.firstName(addAdminRequest.getFirstName())
				.lastName(addAdminRequest.getLastName())
				.emailAddress(addAdminRequest.getEmailAddress())
				.dateOfBirth(addAdminRequest.getDateOfBirth())
				.gender(addAdminRequest.isGender())
				.phone(addAdminRequest.getPhone())
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
				.gender(userRegisterRequest.isGender())
				.phone(userRegisterRequest.getPhone())
				.build();
	}

	static User toAdminEntity(AddAdminRequest addAdminRequest) {
		return User.builder()
				.firstName(addAdminRequest.getFirstName())
				.lastName(addAdminRequest.getLastName())
				.email(addAdminRequest.getEmailAddress())
				.password(StringUtils.getShaStringWithSalt(addAdminRequest.getPassword(),
						addAdminRequest.getEmailAddress()))
				.dateOfBirth(TimeUtils.converToLocalDateTimeNoIso(addAdminRequest.getDateOfBirth()))
				.role(UserRole.ADMIN)
				.phone(addAdminRequest.getPhone())
				.gender(addAdminRequest.isGender())
				.build();
	}

	static List<UserDto> toDtoList(List<User> userList) {
		return userList.stream().map(UserMapper::toDto).collect(Collectors.toList());
	}

	static UserDto toDto(User user) {
		return UserDto.builder()
				.id(user.getId().toString())
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
