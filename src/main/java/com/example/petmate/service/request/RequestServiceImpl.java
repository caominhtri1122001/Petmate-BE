package com.example.petmate.service.request;

import com.example.petmate.entity.Request;
import com.example.petmate.mapper.RequestMapper;
import com.example.petmate.model.request.CreateRequest;
import com.example.petmate.repository.RequestRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RequestServiceImpl implements RequestService {

	private RequestRepository requestRepository;

	public RequestServiceImpl(RequestRepository requestRepository) {
		this.requestRepository = requestRepository;
	}

	@Override
	public boolean createRequest(CreateRequest request) {
		requestRepository.save(RequestMapper.toEntity(request));
		return true;
	}
}
