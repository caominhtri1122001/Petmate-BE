package com.example.petmate.mapper;

import com.example.petmate.entity.Sitter;
import com.example.petmate.entity.User;
import com.example.petmate.model.request.SitterRequest;
import com.example.petmate.model.response.LocationResponse;
import com.example.petmate.model.response.SitterInfoResponse;
import com.example.petmate.model.response.SitterResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper
public interface SitterMapper {

	static Sitter toEntity(SitterRequest request, LocationResponse location) {
		return Sitter.builder()
				.address(location.getAddress())
				.latitude(Float.parseFloat(location.getLatitude()))
				.longitude(Float.parseFloat(location.getLongitude()))
				.yearOfExperience(request.getYearOfExperience())
				.description(request.getDescription())
				.userId(UUID.fromString(request.getUserId()))
				.build();
	}

	static SitterInfoResponse toSitterInfoResponse(Sitter entity, User userEntity, double distance) {
		return SitterInfoResponse.builder()
				.userId(entity.getUserId().toString())
				.sitterId(entity.getId().toString())
				.firstname(userEntity.getFirstName())
				.lastName(userEntity.getLastName())
				.userImage(userEntity.getUserImgUrl())
				.phone(userEntity.getPhone())
				.address(entity.getAddress())
				.emailAddress(userEntity.getEmail())
				.latitude(entity.getLatitude())
				.longitude(entity.getLongitude())
				.yearOfExperience(entity.getYearOfExperience())
				.description(entity.getDescription())
				.distance(distance)
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
				.yearOfExperience(entity.getYearOfExperience())
				.description(entity.getDescription())
				.status(entity.isStatus())
				.build();
	}
}
