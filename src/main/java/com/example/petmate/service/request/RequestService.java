package com.example.petmate.service.request;

import com.example.petmate.entity.Request;
import com.example.petmate.model.request.CreateRequest;
import com.example.petmate.model.response.DetailRequestResponse;
import com.example.petmate.model.response.RequestResponse;
import com.example.petmate.model.response.SchedulesResponse;

import java.util.List;

public interface RequestService {
	boolean createRequest(CreateRequest request);

	List<RequestResponse> getListRequestByUserId(String userId);

	List<RequestResponse> getListRequestBySitterId(String sitterId);

	DetailRequestResponse viewDetailRequest(String requestId);

	boolean acceptRequest(String requestId);

	boolean declineRequest(String requestId);

	boolean doneRequest(String requestId);

	List<SchedulesResponse> getSchedules(String sitterId);
}
