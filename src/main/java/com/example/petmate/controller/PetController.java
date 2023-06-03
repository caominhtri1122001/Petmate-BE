package com.example.petmate.controller;

import com.example.petmate.entity.Pet;
import com.example.petmate.entity.Post;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.PetRequest;
import com.example.petmate.model.response.PetResponse;
import com.example.petmate.service.pet.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pets")
@Slf4j
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @Operation(summary = "api to create pet for user")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to create pet for user", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PetRequest.class)) }) })
    @PostMapping
    public ResponseEntity<PetResponse> createPet(@ModelAttribute PetRequest request) throws ResponseException, IOException {
        log.info("adding new pet");
        log.info(request.getName());
        return ResponseEntity.ok(petService.createPet(request));
    }

    @Operation(summary = "api to update pet for user")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to update pet for user", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PetRequest.class)) }) })
    @PatchMapping("/{id}")
    public Boolean updatePet(@PathVariable String id, @ModelAttribute PetRequest request) throws ResponseException, IOException {
        return petService.updatePet(id,request);
    }

    @Operation(summary = "api to find pet by id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to find pet by id", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPet(@PathVariable String id) throws ResponseException {
        return ResponseEntity.ok(petService.getPetById(id));
    }

    @Operation(summary = "api to get all pet by user_id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get all pet by user_id", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Pet>> getPetOfUser(@PathVariable String userId) throws ResponseException {
        return ResponseEntity.ok(petService.getAllPetsOfUser(userId));
    }

    @Operation(summary = "api to delete pet")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to delete pet", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
    @DeleteMapping("/{id}")
    public Boolean deletePet(@PathVariable String id) throws ResponseException {
        return petService.deletePet(id);
    }

    @Operation(summary = "api to get all pet for admin")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to get all pet for admin", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
    @GetMapping
    public ResponseEntity<List<Pet>> getPets() throws ResponseException {
        return ResponseEntity.ok(petService.getAllPets());
    }
}
