package com.example.petmate.service.request;

import com.example.petmate.entity.Request;
import com.example.petmate.model.request.CreateRequest;
import com.example.petmate.model.response.DetailRequestResponse;
import com.example.petmate.model.response.RequestResponse;

import java.util.List;
import java.util.Optional;

public interface RequestService {
	boolean createRequest(CreateRequest request);

	List<RequestResponse> getListRequestByUserId(String userId);

	List<RequestResponse> getListRequestBySitterId(String sitterId);

	DetailRequestResponse viewDetailRequest(String requestId);
}
