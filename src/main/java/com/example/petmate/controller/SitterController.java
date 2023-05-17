package com.example.petmate.controller;

import com.example.petmate.entity.Sitter;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.SitterRequest;
import com.example.petmate.model.response.LocationResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

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
}
