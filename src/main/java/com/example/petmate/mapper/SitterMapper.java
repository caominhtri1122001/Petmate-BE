package com.example.petmate.mapper;

import com.example.petmate.entity.Sitter;
import com.example.petmate.entity.User;
import com.example.petmate.model.request.SitterRequest;
import com.example.petmate.model.response.SitterResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper
public interface SitterMapper {

	static Sitter toEntity(SitterRequest request) {
		return Sitter.builder()
				.address(request.getAddress())
				.city(request.getCity())
				.postcode(request.getPostCode())
				.yearOfExperience(request.getYearOfExperience())
				.description(request.getDescription())
				.userId(UUID.fromString(request.getUserId()))
				.build();
	}

	static SitterResponse toResponse(Sitter entity, User userEntity) {
		return SitterResponse.builder()
				.requestId(entity.getId().toString())
				.userId(entity.getUserId().toString())
				.firstname(userEntity.getFirstName())
				.lastName(userEntity.getLastName())
				.gender(userEntity.isGender())
				.dateOfBirth(userEntity.getDateOfBirth().toString())
				.phone(userEntity.getPhone())
				.address(entity.getAddress())
				.city(entity.getCity())
				.postCode(entity.getPostcode())
				.yearOfExperience(entity.getYearOfExperience())
				.description(entity.getDescription())
				.status(entity.isStatus())
				.build();
	}
}
