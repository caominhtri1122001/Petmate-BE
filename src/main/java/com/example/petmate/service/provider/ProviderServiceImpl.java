package com.example.petmate.service.provider;

import com.example.petmate.constant.ResponseCodes;
import com.example.petmate.entity.Provider;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.mapper.ProviderMapper;
import com.example.petmate.model.request.ProviderRequest;
import com.example.petmate.model.response.ProviderResponse;
import com.example.petmate.repository.ProviderRepository;
import com.example.petmate.repository.SitterRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProviderServiceImpl implements ProviderService {

	private final ProviderRepository providerRepository;

	private final SitterRepository sitterRepository;

	public ProviderServiceImpl(ProviderRepository providerRepository, SitterRepository sitterRepository) {
		this.providerRepository = providerRepository;
		this.sitterRepository = sitterRepository;
	}

	@Override
	public boolean createServiceForSitter(ProviderRequest request) {
		Optional<Provider> entity = providerRepository.findByNameAndSitterId(request.getName(), UUID.fromString(request.getSitterId()));
		if (entity.isPresent()) {
			throw new ResponseException(ResponseCodes.PM_ALREADY_HAS_SERVICE);
		}
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
		if (providers.isEmpty()) {
			return null;
		}
		Stream<Provider> providerStream = providers.stream().flatMap(Collection::stream);
		List<Provider> filteredProviders = providerStream.filter(provider -> !provider.isDisable())
				.collect(Collectors.toList());
		return ProviderMapper.toListResponse(filteredProviders);
	}

	@Override
	public List<ProviderResponse> getListServiceByUser(String userId) {
		UUID sitterId = sitterRepository.findByUserId(UUID.fromString(userId)).get().getId();
		Optional<List<Provider>> providers = providerRepository.findBySitterId(sitterId);
		if (providers.isEmpty()) {
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

		provider.get().setDisable(true);
		providerRepository.save(provider.get());
		return true;
	}

	@Override
	public boolean enableService(String serviceId) {
		Optional<Provider> provider = providerRepository.findById(UUID.fromString(serviceId));
		if (provider.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}

		provider.get().setDisable(false);
		providerRepository.save(provider.get());
		return true;
	}
}
