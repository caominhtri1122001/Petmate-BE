package com.example.petmate.service.petservice;

import com.example.petmate.constant.ResponseCodes;
import com.example.petmate.dto.StoreServiceDto;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.mapper.ServiceMapper;
import com.example.petmate.model.request.ServiceRequest;
import com.example.petmate.repository.ServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class StoreServiceImpl implements StoreService {

	private final ServiceRepository serviceRepository;

	public StoreServiceImpl(ServiceRepository serviceRepository) {
		this.serviceRepository = serviceRepository;
	}

	@Override
	public StoreServiceDto createService(ServiceRequest request) {
		return ServiceMapper.toDto(serviceRepository.save(ServiceMapper.toEntity(request)));
	}

	@Override
	public boolean updateService(String serviceId, ServiceRequest request) {
		Optional<com.example.petmate.entity.Service> service = serviceRepository.findById(UUID.fromString(serviceId));
		if (service.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		service.get().setName(request.getName());
		service.get().setDescription(request.getDescription());
		service.get().setPrice(request.getPrice());
		service.get().setType(request.getType());
		serviceRepository.save(service.get());
		return true;
	}

	@Override
	public StoreServiceDto getServiceById(String serviceId) {
		Optional<com.example.petmate.entity.Service> service = serviceRepository.findById(UUID.fromString(serviceId));
		if (service.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		return ServiceMapper.toDto(service.get());
	}

	@Override
	public List<StoreServiceDto> getAllServices() {
		return ServiceMapper.toListDto(serviceRepository.findAll());
	}

	@Override
	public boolean deleteService(String serviceId) {
		Optional<com.example.petmate.entity.Service> service = serviceRepository.findById(UUID.fromString(serviceId));
		if (service.isPresent()) {
			serviceRepository.deleteById(UUID.fromString(serviceId));
		} else {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		return true;
	}
}
