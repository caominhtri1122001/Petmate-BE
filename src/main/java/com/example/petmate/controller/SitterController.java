package com.example.petmate.controller;

import com.example.petmate.entity.Sitter;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.SitterRequest;
import com.example.petmate.model.response.LocationResponse;
import com.example.petmate.model.response.SitterInfoResponse;
import com.example.petmate.model.response.SitterResponse;
import com.example.petmate.service.sitter.SitterService;
import com.example.petmate.service.third_party.locationIQ.LocationIqService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sitter")
public class SitterController {

	private final SitterService sitterService;

	private final LocationIqService locationIqService;

	public SitterController(SitterService sitterService, LocationIqService locationIqService) {
		this.sitterService = sitterService;
		this.locationIqService = locationIqService;
	}

	@Operation(summary = "api to become a sitter")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to become a sitter", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Sitter.class)) }) })
	@PostMapping("/register")
	public ResponseEntity<SitterResponse> becomeSitter(@RequestBody SitterRequest sitterRequest) throws ResponseException {
		return ResponseEntity.ok(sitterService.createSitter(sitterRequest));
	}

	@Operation(summary = "api to get the location")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to become a sitter", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LocationResponse.class)) }) })
	@GetMapping("/getLocation")
	public LocationResponse getLocation(String address) throws ResponseException {
		return locationIqService.getTheLocation(address);
	}

	@Operation(summary = "api to get sitter around user")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get sitter around user", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SitterInfoResponse.class)) }) })
	@GetMapping("/getSitter")
	public ResponseEntity<List<SitterInfoResponse>> getSitterAround() throws ResponseException {
		return ResponseEntity.ok(sitterService.getListSitter());
	}

	@Operation(summary = "api to get requests to become sitter")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get requests to become sitter", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SitterInfoResponse.class)) }) })
	@GetMapping("/requests")
	public ResponseEntity<List<SitterInfoResponse>> getRequest() throws ResponseException {
		return ResponseEntity.ok(sitterService.getListSitterRequest());
	}

	@Operation(summary = "api to accept request to become sitter")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to accept request to become sitter", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@GetMapping("/requests/{id}")
	public boolean acceptRequest(@PathVariable String id) throws ResponseException, MessagingException, UnsupportedEncodingException {
		return sitterService.acceptRequestSitter(id);
	}

	@Operation(summary = "api to delete request to become sitter")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to delete request to become sitter", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@DeleteMapping("/requests/{id}")
	public boolean deleteRequest(@PathVariable String id) throws ResponseException, MessagingException, UnsupportedEncodingException {
		return sitterService.deleteRequestSitter(id);
	}
}
