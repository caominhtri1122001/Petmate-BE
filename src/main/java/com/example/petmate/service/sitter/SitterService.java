package com.example.petmate.service.sitter;

import com.example.petmate.entity.Sitter;
import com.example.petmate.model.request.SitterRequest;
import com.example.petmate.model.response.SitterInfoResponse;
import com.example.petmate.model.response.SitterResponse;

import java.util.List;

public interface SitterService {
	SitterResponse createSitter(SitterRequest request);

	List<SitterInfoResponse> getListSitter();

	List<SitterInfoResponse> getListSitterRequest();

	boolean acceptRequestSitter(String id);

	boolean deleteRequestSitter(String id);
}
