package com.example.petmate.mapper;

import com.example.petmate.constant.UserRole;
import com.example.petmate.entity.User;
import com.example.petmate.model.request.UserRegisterRequest;
import com.example.petmate.model.response.UserRegisterResponse;
import com.example.petmate.utils.StringUtils;
import com.example.petmate.utils.TimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
				.fisrtName(userRegisterRequest.getFirstName())
				.lastName(userRegisterRequest.getLastName())
				.email(userRegisterRequest.getEmailAddress())
				.password(StringUtils.getShaStringWithSalt(userRegisterRequest.getPassword(),
						userRegisterRequest.getEmailAddress()))
				.dateOfBirth(TimeUtils.converToLocalDateTimeNoIso(userRegisterRequest.getDateOfBirth()))
				.role(UserRole.CUSTOMER)
				.build();
	}

}
