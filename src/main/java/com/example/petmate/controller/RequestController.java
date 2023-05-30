package com.example.petmate.controller;

import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.CreateRequest;
import com.example.petmate.service.request.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/requests")
public class RequestController {

	private final RequestService requestService;

	public RequestController(RequestService requestService) {
		this.requestService = requestService;
	}

	@Operation(summary = "api to send a request")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to send a request", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreateRequest.class)) }) })
	@PostMapping
	public boolean sendRequest(@RequestBody CreateRequest request) throws ResponseException {
		return requestService.createRequest(request);
	}
}