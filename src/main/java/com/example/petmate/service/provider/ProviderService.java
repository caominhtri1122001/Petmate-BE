package com.example.petmate.service.provider;

import com.example.petmate.entity.Provider;
import com.example.petmate.model.request.ProviderRequest;
import com.example.petmate.model.response.ProviderResponse;

import java.util.List;

public interface ProviderService {
	boolean createServiceForSitter(ProviderRequest request);

	boolean updateServiceForSitter(String serviceId, float price);

	Provider getServiceById(String serviceId);

	List<ProviderResponse> getListServiceBySitter(String sitterId);

	List<ProviderResponse> getListServiceByUser(String userId);

	boolean removeService(String serviceId);

	boolean enableService(String serviceId);
}
