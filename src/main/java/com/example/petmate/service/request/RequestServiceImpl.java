package com.example.petmate.service.request;

import com.example.petmate.entity.Request;
import com.example.petmate.entity.User;
import com.example.petmate.mapper.RequestMapper;
import com.example.petmate.model.request.CreateRequest;
import com.example.petmate.model.response.RequestResponse;
import com.example.petmate.repository.PetRepository;
import com.example.petmate.repository.ProviderRepository;
import com.example.petmate.repository.RequestRepository;
import com.example.petmate.repository.SitterRepository;
import com.example.petmate.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RequestServiceImpl implements RequestService {

	private final RequestRepository requestRepository;

	private final PetRepository petRepository;

	private final ProviderRepository providerRepository;

	private final SitterRepository sitterRepository;

	private final UserRepository userRepository;

	public RequestServiceImpl(RequestRepository requestRepository, PetRepository petRepository,
			ProviderRepository providerRepository, SitterRepository sitterRepository, UserRepository userRepository) {
		this.requestRepository = requestRepository;
		this.petRepository = petRepository;
		this.providerRepository = providerRepository;
		this.sitterRepository = sitterRepository;
		this.userRepository = userRepository;
	}

	@Override
	public boolean createRequest(CreateRequest request) {
		requestRepository.save(RequestMapper.toEntity(request));
		return true;
	}

	@Override
	public List<RequestResponse> getListRequestByUserId(String userId) {
		List<Request> requests = requestRepository.findByUserId(UUID.fromString(userId));
		List<RequestResponse> result = new ArrayList<>();
		requests.forEach(request -> {
			String petName = petRepository.findById(request.getPetId()).get().getName();
			String serviceName = providerRepository.findById(request.getServiceId()).get().getName();
			float price = providerRepository.findById(request.getServiceId()).get().getPrice();
			String getUserId = sitterRepository.findById(request.getSitterId()).get().getUserId().toString();
			Optional<User> userEntity = userRepository.findById(UUID.fromString(getUserId));
			String sitterName = userEntity.get().getFirstName() + " " + userEntity.get().getLastName();
			String sitterAvatar = userEntity.get().getUserImgUrl();
			Optional<User> customer = userRepository.findById(UUID.fromString(userId));
			String customerName = customer.get().getFirstName() + customer.get().getLastName();
			String customerAvatar = customer.get().getUserImgUrl();
			result.add(RequestMapper.toResponse(request, petName, sitterName, sitterAvatar, serviceName, price,
					customerName, customerAvatar));
		});
		return result;
	}

	@Override
	public List<RequestResponse> getListRequestBySitterId(String sitterId) {
		List<Request> requests = requestRepository.findBySitterId(UUID.fromString(sitterId));
		List<RequestResponse> result = new ArrayList<>();
		requests.forEach(request -> {
			String petName = petRepository.findById(request.getPetId()).get().getName();
			String serviceName = providerRepository.findById(request.getServiceId()).get().getName();
			float price = providerRepository.findById(request.getServiceId()).get().getPrice();
			String getUserId = sitterRepository.findById(request.getSitterId()).get().getUserId().toString();
			Optional<User> userEntity = userRepository.findById(UUID.fromString(getUserId));
			String sitterName = userEntity.get().getFirstName() + " " + userEntity.get().getLastName();
			String sitterAvatar = userEntity.get().getUserImgUrl();
			Optional<User> customer = userRepository.findById(request.getUserId());
			String customerName = customer.get().getFirstName() + customer.get().getLastName();
			String customerAvatar = customer.get().getUserImgUrl();
			result.add(RequestMapper.toResponse(request, petName, sitterName, sitterAvatar, serviceName, price,
					customerName, customerAvatar));
		});
		return result;
	}
}
