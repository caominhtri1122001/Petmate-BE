package com.example.petmate.mapper;

import com.example.petmate.entity.Request;
import com.example.petmate.model.request.CreateRequest;
import com.example.petmate.model.response.RequestResponse;
import com.example.petmate.utils.TimeUtils;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper
public interface RequestMapper {
	static Request toEntity (CreateRequest request) {
		return Request.builder()
				.userId(UUID.fromString(request.getUserId()))
				.sitterId(UUID.fromString(request.getSitterId()))
				.petId(UUID.fromString(request.getPetId()))
				.serviceId(UUID.fromString(request.getServiceId()))
				.startDate(TimeUtils.convertStringToDate(request.getStartDate()))
				.endDate(TimeUtils.convertStringToDate(request.getEndDate()))
				.startTime(TimeUtils.convertStringToTime(request.getStartTime()))
				.endTime(TimeUtils.convertStringToTime(request.getEndTime()))
				.address(request.getAddress())
				.message(request.getMessage())
				.status(false)
				.build();
	}

	static RequestResponse toResponse(Request entity, String petName, String sitterName,String sitterAvatar, String serviceName, float price) {
		return RequestResponse.builder()
				.requestId(entity.getId().toString())
				.userId(entity.getUserId().toString())
				.sitterId(entity.getSitterId().toString())
				.sitterName(sitterName)
				.sitterAvatar(sitterAvatar)
				.petId(entity.getPetId().toString())
				.petName(petName)
				.serviceId(entity.getServiceId().toString())
				.serviceName(serviceName)
				.price(price)
				.startDate(entity.getStartDate().toString())
				.endDate(entity.getEndDate().toString())
				.startTime(entity.getStartTime().toString())
				.endTime(entity.getEndTime().toString())
				.address(entity.getAddress())
				.message(entity.getMessage())
				.status(entity.isStatus())
				.build();
	}
}
