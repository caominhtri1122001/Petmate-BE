package com.example.petmate.mapper;

import com.example.petmate.dto.StoreServiceDto;
import com.example.petmate.entity.Service;
import com.example.petmate.model.request.ServiceRequest;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface ServiceMapper {

	static List<StoreServiceDto> toListDto(List<Service> services) {
		return services.stream().map(ServiceMapper::toDto).collect(Collectors.toList());
	}

	static StoreServiceDto toDto(Service service) {
		return StoreServiceDto.builder()
				.id(service.getId().toString())
				.name(service.getName())
				.description(service.getDescription())
				.price(service.getPrice())
				.type(service.getType())
				.build();
	}

	static Service toEntity(ServiceRequest request) {
		return Service.builder()
				.name(request.getName())
				.description(request.getDescription())
				.price(request.getPrice())
				.type(request.getType())
				.build();
	}
}