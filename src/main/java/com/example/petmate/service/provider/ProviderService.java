package com.example.petmate.service.provider;

import com.example.petmate.entity.Provider;

import java.util.List;

public interface ProviderService {
	boolean createServiceForSitter(Provider request);

	boolean updateServiceForSitter(String serviceId, float price);

	Provider getServiceById(String serviceId);

	List<Provider> getListServiceBySitter(String sitterId);

	boolean removeService(String serviceId);
}
