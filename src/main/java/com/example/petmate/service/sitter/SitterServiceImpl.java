package com.example.petmate.service.sitter;

import com.example.petmate.constant.ResponseCodes;
import com.example.petmate.entity.Sitter;
import com.example.petmate.entity.User;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.mapper.SitterMapper;
import com.example.petmate.model.request.SitterRequest;
import com.example.petmate.model.response.SitterResponse;
import com.example.petmate.repository.SitterRepository;
import com.example.petmate.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SitterServiceImpl implements SitterService {

	private final SitterRepository sitterRepository;
	private final UserRepository userRepository;

	public SitterServiceImpl(SitterRepository sitterRepository, UserRepository userRepository) {
		this.sitterRepository = sitterRepository;
		this.userRepository = userRepository;
	}

	@Override
	public SitterResponse createSitter(SitterRequest request) {
		Optional<Sitter> sitter = sitterRepository.findByUserId(UUID.fromString(request.getUserId()));
		if (sitter.isPresent()) {
			throw new ResponseException(ResponseCodes.RT_ERROR_ALREADY_REQUESTED);
		}
		Optional<User> user = userRepository.findById(UUID.fromString(request.getUserId()));
		return SitterMapper.toResponse(sitterRepository.save(SitterMapper.toEntity(request)), user.get());
	}
}
