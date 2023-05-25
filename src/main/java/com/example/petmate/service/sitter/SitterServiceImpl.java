package com.example.petmate.service.sitter;

import com.example.petmate.constant.ResponseCodes;
import com.example.petmate.constant.UserRole;
import com.example.petmate.entity.Sitter;
import com.example.petmate.entity.User;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.mapper.SitterMapper;
import com.example.petmate.model.request.SitterRequest;
import com.example.petmate.model.response.LocationResponse;
import com.example.petmate.model.response.SitterInfoResponse;
import com.example.petmate.model.response.SitterResponse;
import com.example.petmate.repository.SitterRepository;
import com.example.petmate.repository.UserRepository;
import com.example.petmate.service.third_party.locationIQ.LocationIqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SitterServiceImpl implements SitterService {

	private final SitterRepository sitterRepository;
	private final UserRepository userRepository;

	private final LocationIqService locationIqService;

	public SitterServiceImpl(SitterRepository sitterRepository, UserRepository userRepository,
			LocationIqService locationIqService) {
		this.sitterRepository = sitterRepository;
		this.userRepository = userRepository;
		this.locationIqService = locationIqService;
	}

	@Override
	public SitterResponse createSitter(SitterRequest request) {
		log.info(request.getAddress());
		Optional<Sitter> sitter = sitterRepository.findByUserId(UUID.fromString(request.getUserId()));
		if (sitter.isPresent()) {
			throw new ResponseException(ResponseCodes.RT_ERROR_ALREADY_REQUESTED);
		}
		LocationResponse result = locationIqService.getTheLocation(
				request.getAddress() + " " + request.getDistrict() + " " + request.getCity());
		if (Objects.isNull(result)) {
			throw new ResponseException(ResponseCodes.RT_ERROR_WRONG_ADDRESS);
		}
		Optional<User> user = userRepository.findById(UUID.fromString(request.getUserId()));
		return SitterMapper.toResponse(sitterRepository.save(SitterMapper.toEntity(request,result)), user.get());
	}

	@Override
	public List<SitterInfoResponse> getListSitter() {
		List<SitterInfoResponse> result = new ArrayList<>();
		List<Sitter> sitters = sitterRepository.findAll();
		sitters.forEach(sitter -> {
			Optional<User> user = userRepository.findById(sitter.getUserId());
			result.add(SitterMapper.toSitterInfoResponse(sitter, user.get()));
		});
		return result;
	}

	@Override
	public List<SitterInfoResponse> getListSitterRequest() {
		List<SitterInfoResponse> result = new ArrayList<>();
		List<Sitter> sitters = sitterRepository.findByStatus(false);
		sitters.forEach(sitter -> {
			Optional<User> user = userRepository.findById(sitter.getUserId());
			result.add(SitterMapper.toSitterInfoResponse(sitter, user.get()));
		});
		return result;
	}

	@Override
	public boolean acceptRequestSitter(String id) {
		Optional<Sitter> sitter = sitterRepository.findByUserId(UUID.fromString(id));
		if (sitter.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		sitter.get().setStatus(true);

		sitterRepository.save(sitter.get());
		Optional<User> user = userRepository.findById(sitter.get().getUserId());
		user.get().setRole(UserRole.EMPLOYEE);
		userRepository.save(user.get());

		return true;
	}

	@Override
	public boolean deleteRequestSitter(String id) {
		Optional<Sitter> sitter = sitterRepository.findByUserId(UUID.fromString(id));
		if (sitter.isEmpty()) {
			throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
		}
		sitterRepository.delete(sitter.get());
		return true;
	}
}
