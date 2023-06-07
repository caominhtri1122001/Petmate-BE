package com.example.petmate.controller;

import com.example.petmate.dto.StoreServiceDto;
import com.example.petmate.entity.Provider;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.ProviderRequest;
import com.example.petmate.model.request.ServiceRequest;
import com.example.petmate.model.response.ProviderResponse;
import com.example.petmate.service.provider.ProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/provider")
public class ProviderController {
	private final ProviderService providerService;

	public ProviderController(ProviderService providerService) {
		this.providerService = providerService;
	}

	@Operation(summary = "api to get all services")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get all services", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProviderResponse.class)) }) })
	@GetMapping("/services/{id}")
	public ResponseEntity<List<ProviderResponse>> getAllServices(@PathVariable String id) throws ResponseException {
		return ResponseEntity.ok(providerService.getListServiceBySitter(id));
	}

	@Operation(summary = "api to get all services by userid")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get all services by userid", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProviderResponse.class)) }) })
	@GetMapping("/services/user/{id}")
	public ResponseEntity<List<ProviderResponse>> getAllServicesByUserId(@PathVariable String id) throws ResponseException {
		return ResponseEntity.ok(providerService.getListServiceByUser(id));
	}

	@Operation(summary = "api to create service")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to create service", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProviderRequest.class)) }) })
	@PostMapping
	public boolean createService(@RequestBody ProviderRequest request) throws ResponseException {
		return providerService.createServiceForSitter(request);
	}

	@Operation(summary = "api to update service")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to update service", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Provider.class)) }) })
	@PatchMapping(path = "/{id}")
	public ResponseEntity<Boolean> updateService(@PathVariable String id,float price) throws ResponseException {
		return ResponseEntity.ok(providerService.updateServiceForSitter(id,price));
	}

	@Operation(summary = "api to get service by id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get service by id", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Provider.class)) }) })
	@GetMapping(path = "/{id}")
	public ResponseEntity<Provider> getServiceById(@PathVariable String id) throws ResponseException {
		return ResponseEntity.ok(providerService.getServiceById(id));
	}

	@Operation(summary = "api to delete service")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to delete service", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Boolean> deleteService(@PathVariable String id) throws ResponseException {
		return ResponseEntity.ok(providerService.removeService(id));
	}

	@Operation(summary = "api to enable service")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to enable service", content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@GetMapping(path = "/enable/{id}")
	public ResponseEntity<Boolean> enableService(@PathVariable String id) throws ResponseException {
		return ResponseEntity.ok(providerService.enableService(id));
	}
}
