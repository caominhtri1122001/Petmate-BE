package com.example.petmate.service.sitter;

import com.example.petmate.entity.Sitter;
import com.example.petmate.model.request.SitterRequest;
import com.example.petmate.model.response.SitterResponse;

public interface SitterService {
	SitterResponse createSitter(SitterRequest request);
}
