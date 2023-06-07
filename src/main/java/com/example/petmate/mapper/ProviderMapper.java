package com.example.petmate.mapper;

import com.example.petmate.entity.Provider;
import com.example.petmate.model.request.ProviderRequest;
import com.example.petmate.model.response.ProviderResponse;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper
public interface ProviderMapper {
	static Provider toEntity(ProviderRequest request) {
		return Provider.builder()
				.name(request.getName())
				.price(request.getPrice())
				.sitterId(UUID.fromString(request.getSitterId()))
				.disable(false)
				.build();
	}

	static List<ProviderResponse> toListResponse(List<Provider> entity) {
		return entity.stream().map(ProviderMapper::toResponse).collect(Collectors.toList());
	}

	static ProviderResponse toResponse(Provider entity) {
		return ProviderResponse.builder()
				.id(entity.getId().toString())
				.name(entity.getName())
				.price(entity.getPrice())
				.sitterId(entity.getSitterId().toString())
				.disable(entity.isDisable())
				.build();
	}
}
