package com.example.petmate.service.sitter;

import com.example.petmate.entity.Sitter;
import com.example.petmate.model.request.SitterRequest;
import com.example.petmate.model.response.SitterInfoResponse;
import com.example.petmate.model.response.SitterResponse;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface SitterService {
	SitterResponse createSitter(SitterRequest request);

	List<SitterInfoResponse> getListSitter(double lat, double lng);

	List<SitterInfoResponse> getListSitterRequest();

	SitterInfoResponse getSitterById(String sitterId);

	SitterInfoResponse getSitterByUserId(String userId);

	boolean acceptRequestSitter(String id) throws MessagingException, UnsupportedEncodingException;

	boolean deleteRequestSitter(String id) throws MessagingException, UnsupportedEncodingException;
}
