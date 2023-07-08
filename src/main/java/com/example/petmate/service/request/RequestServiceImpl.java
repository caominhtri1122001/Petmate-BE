package com.example.petmate.service.request;

import com.example.petmate.constant.ResponseCodes;
import com.example.petmate.constant.StatusType;
import com.example.petmate.entity.Provider;
import com.example.petmate.entity.Request;
import com.example.petmate.entity.User;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.mapper.RequestMapper;
import com.example.petmate.model.request.CreateRequest;
import com.example.petmate.model.response.DetailRequestResponse;
import com.example.petmate.model.response.RequestResponse;
import com.example.petmate.model.response.SchedulesResponse;
import com.example.petmate.repository.PetRepository;
import com.example.petmate.repository.ProviderRepository;
import com.example.petmate.repository.RequestRepository;
import com.example.petmate.repository.SitterRepository;
import com.example.petmate.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
	public Request createRequest(CreateRequest request) {
		Request newRequest = RequestMapper.toEntity(request);

		List<Request> requests = requestRepository.findByPetId(newRequest.getPetId());
		List<Request> requestSchedules = requests.stream()
				.filter(rq -> StatusType.PENDING.getType().equals(rq.getStatus()) && rq.isValid())
				.collect(Collectors.toList());
		List<Request> acceptedRequests = requests.stream()
				.filter(rq -> StatusType.ACCEPT.getType().equals(rq.getStatus()) && rq.isValid())
				.collect(Collectors.toList());
		requestSchedules.addAll(acceptedRequests);
		if (validateRequest(newRequest, requestSchedules)) {
			throw new ResponseException(ResponseCodes.PM_NOT_AVAILABLE);
		}

		return requestRepository.save(newRequest);
	}

	@Override
	public List<RequestResponse> getListAllRequest() {
		List<Request> requests = requestRepository.findAll();
		requests = requests.stream().filter(Request::isValid).collect(Collectors.toList());
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

	@Override
	public List<RequestResponse> getListRequestByUserId(String userId) {
		List<Request> requests = requestRepository.findByUserId(UUID.fromString(userId));
		requests = requests.stream().filter(Request::isValid).collect(Collectors.toList());
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
		requests = requests.stream().filter(Request::isValid).collect(Collectors.toList());
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

	@Override
	public DetailRequestResponse viewDetailRequest(String requestId) {
		Optional<Request> request = requestRepository.findById(UUID.fromString(requestId));
		if (request.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		Optional<Provider> provider = providerRepository.findById(request.get().getServiceId());
		String name = provider.get().getName();
		float price = provider.get().getPrice();
		return RequestMapper.toDetailResponse(request.get(), name, price);
	}

	@Override
	public boolean acceptRequest(String requestId) {
		Optional<Request> request = requestRepository.findById(UUID.fromString(requestId));
		if (request.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		List<Request> requests = requestRepository.findBySitterId(request.get().getSitterId());
		List<Request> requestSchedules = requests.stream()
				.filter(rq -> StatusType.ACCEPT.getType().equals(rq.getStatus()) && rq.isValid())
				.collect(Collectors.toList());
		if (validateRequest(request.get(), requestSchedules)) {
			return false;
		}
		request.get().setStatus(StatusType.ACCEPT.getType());
		requestRepository.save(request.get());
		return true;
	}

	@Override
	public boolean declineRequest(String requestId) {
		Optional<Request> request = requestRepository.findById(UUID.fromString(requestId));
		if (request.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		request.get().setStatus(StatusType.CANCEL.getType());
		requestRepository.save(request.get());
		return true;
	}

	@Override
	public boolean cancelRequest(String requestId) {
		Optional<Request> request = requestRepository.findById(UUID.fromString(requestId));
		if (request.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		if (validateDate(request.get())) {
			return false;
		}

		request.get().setStatus(StatusType.CANCEL.getType());
		requestRepository.save(request.get());
		return true;
	}

	@Override
	public boolean doneRequest(String requestId) {
		Optional<Request> request = requestRepository.findById(UUID.fromString(requestId));
		if (request.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		request.get().setStatus(StatusType.DONE.getType());
		requestRepository.save(request.get());
		return true;
	}

	@Override
	public boolean payRequest(String requestId) {
		Optional<Request> request = requestRepository.findById(UUID.fromString(requestId));
		if (request.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		request.get().setValid(true);
		requestRepository.save(request.get());
		return true;
	}

	@Override
	public List<SchedulesResponse> getSchedules(String sitterId) {
		List<SchedulesResponse> result = new ArrayList<>();
		List<Request> requests = requestRepository.findBySitterId(UUID.fromString(sitterId));
		requests.forEach(request -> {
			if (request.getStatus().equals(StatusType.ACCEPT.getType())) {
				String serviceName = providerRepository.findById(request.getServiceId()).get().getName();
				String petName = petRepository.findById(request.getPetId()).get().getName();
				result.add(RequestMapper.toSchedulesResponse(request, petName, serviceName));
			}
		});
		return result;
	}

	private boolean validateDate(Request request) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime dateTime = LocalDateTime.parse(request.getStartDate() + "T" + request.getStartTime());
		Duration duration = Duration.between(dateTime, now);
		return duration.abs().toHours() <= 2;
	}

	private boolean validateRequest(Request newRequest, List<Request> oldRequest) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime startDateTime = LocalDateTime.parse(newRequest.getStartDate() + " " + newRequest.getStartTime(),
				formatter);
		LocalDateTime endDateTime = LocalDateTime.parse(newRequest.getEndDate() + " " + newRequest.getEndTime(),
				formatter);
		for (Request existingRequest : oldRequest) {
			LocalDateTime existingStartDateTime = LocalDateTime.parse(
					existingRequest.getStartDate() + " " + existingRequest.getStartTime(), formatter);
			LocalDateTime existingEndDateTime = LocalDateTime.parse(
					existingRequest.getEndDate() + " " + existingRequest.getEndTime(), formatter);
			if ((startDateTime.isAfter(existingStartDateTime) && startDateTime.isBefore(existingEndDateTime)) ||
					(endDateTime.isAfter(existingStartDateTime) && endDateTime.isBefore(existingEndDateTime)) ||
					(startDateTime.isEqual(existingStartDateTime) || endDateTime.isEqual(existingEndDateTime)) ||
					(existingStartDateTime.isAfter(startDateTime) && existingStartDateTime.isBefore(endDateTime)) ||
					(existingEndDateTime.isAfter(startDateTime) && existingEndDateTime.isBefore(endDateTime)) ||
					(existingStartDateTime.isEqual(startDateTime) && existingEndDateTime.isEqual(endDateTime))) {
				return true;
			}
		}
		return false;
	}
}
