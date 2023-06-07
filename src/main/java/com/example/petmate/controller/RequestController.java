package com.example.petmate.controller;

import com.example.petmate.entity.Request;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.CreateRequest;
import com.example.petmate.model.response.DetailRequestResponse;
import com.example.petmate.model.response.RequestResponse;
import com.example.petmate.service.request.RequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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

	@Operation(summary = "api to get all request by user id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get all request by user id", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Request.class)) }) })
	@GetMapping("/{id}")
	public ResponseEntity<List<RequestResponse>> getListRequest(@PathVariable String id) throws ResponseException {
		return ResponseEntity.ok(requestService.getListRequestByUserId(id));
	}

	@Operation(summary = "api to get all request by sitter id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get all request by sitter id", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Request.class)) }) })
	@GetMapping("/sitter/{id}")
	public ResponseEntity<List<RequestResponse>> getListRequestBySitter(@PathVariable String id) throws ResponseException {
		return ResponseEntity.ok(requestService.getListRequestBySitterId(id));
	}

	@Operation(summary = "api to get request by id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get request by id", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DetailRequestResponse.class)) }) })
	@GetMapping("/detail/{id}")
	public ResponseEntity<DetailRequestResponse> viewDetailRequest(@PathVariable String id) throws ResponseException {
		return ResponseEntity.ok(requestService.viewDetailRequest(id));
	}
}
