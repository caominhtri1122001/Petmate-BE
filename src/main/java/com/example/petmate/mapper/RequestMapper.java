package com.example.petmate.mapper;

import com.example.petmate.entity.Request;
import com.example.petmate.model.request.CreateRequest;
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
				.startDate(TimeUtils.convertStringToDate(request.getStartDate()))
				.endDate(TimeUtils.convertStringToDate(request.getEndDate()))
				.startTime(TimeUtils.convertStringToTime(request.getStartTime()))
				.endTime(TimeUtils.convertStringToTime(request.getEndTime()))
				.address(request.getAddress())
				.message(request.getMessage())
				.status(false)
				.build();
	}
}
