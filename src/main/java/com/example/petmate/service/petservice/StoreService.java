package com.example.petmate.service.petservice;

import com.example.petmate.dto.StoreServiceDto;
import com.example.petmate.model.request.ServiceRequest;

import java.util.List;

public interface StoreService {
	StoreServiceDto createService(ServiceRequest request);

	boolean updateService(String serviceId, ServiceRequest request);

	StoreServiceDto getServiceById(String serviceId);

	List<StoreServiceDto> getAllServices();

	boolean deleteService(String serviceId);
}
