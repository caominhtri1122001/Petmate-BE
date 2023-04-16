package com.example.petmate.controller;

import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.CreatePetRequest;
import com.example.petmate.model.response.CreatePetResponse;
import com.example.petmate.service.pet.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @Operation(summary = "api to create pet for user")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to create pet for user", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreatePetRequest.class)) }) })
    @PostMapping
    public ResponseEntity<CreatePetResponse> createPet(CreatePetRequest request) throws ResponseException {
        return ResponseEntity.ok(petService.petCreate(request));
    }
}
