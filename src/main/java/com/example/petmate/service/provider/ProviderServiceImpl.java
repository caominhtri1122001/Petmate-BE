package com.example.petmate.service.provider;

import com.example.petmate.constant.ResponseCodes;
import com.example.petmate.entity.Provider;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.mapper.ProviderMapper;
import com.example.petmate.model.request.ProviderRequest;
import com.example.petmate.model.response.ProviderResponse;
import com.example.petmate.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProviderServiceImpl implements ProviderService {

	private final ProviderRepository providerRepository;

	public ProviderServiceImpl(ProviderRepository providerRepository) {
		this.providerRepository = providerRepository;
	}

	@Override
	public boolean createServiceForSitter(ProviderRequest request) {
		providerRepository.save(ProviderMapper.toEntity(request));
		return true;
	}

	@Override
	public boolean updateServiceForSitter(String serviceId, float price) {
		Optional<Provider> provider = providerRepository.findById(UUID.fromString(serviceId));
		if (provider.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		provider.get().setPrice(price);
		providerRepository.save(provider.get());
		return true;
	}

	@Override
	public Provider getServiceById(String serviceId) {
		Optional<Provider> provider = providerRepository.findById(UUID.fromString(serviceId));
		if (provider.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		return provider.get();
	}

	@Override
	public List<ProviderResponse> getListServiceBySitter(String sitterId) {
		Optional<List<Provider>> providers = providerRepository.findBySitterId(UUID.fromString(sitterId));
		if(providers.isEmpty()) {
			return null;
		}
		return ProviderMapper.toListResponse(providers.get());
	}

	@Override
	public boolean removeService(String serviceId) {
		Optional<Provider> provider = providerRepository.findById(UUID.fromString(serviceId));
		if (provider.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		providerRepository.deleteById(UUID.fromString(serviceId));
		return true;
	}
}
