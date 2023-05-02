package com.example.petmate.controller;

import com.example.petmate.dto.UserDto;
import com.example.petmate.entity.Pet;
import com.example.petmate.entity.User;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.AddEmployeeRequest;
import com.example.petmate.model.request.PetRequest;
import com.example.petmate.model.request.UpdateEmployeeRequest;
import com.example.petmate.model.request.UserRegisterRequest;
import com.example.petmate.model.response.AddEmployeeResponse;
import com.example.petmate.model.response.UserRegisterResponse;
import com.example.petmate.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "api to add employee")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to add employee", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AddEmployeeRequest.class)) }) })
    @PostMapping("/employee")
    public AddEmployeeResponse addEmployee(AddEmployeeRequest addEmployeeRequest) throws ResponseException {
        return userService.addEmployee(addEmployeeRequest);
    }

    @Operation(summary = "api to update user for admin")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to update user for admin", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UpdateEmployeeRequest.class)) }) })
    @PatchMapping("/employee/{id}")
    public Boolean updatePet(@PathVariable String id, UpdateEmployeeRequest request) throws ResponseException {
        return userService.updateEmployee(id, request);
    }

    @Operation(summary = "api to find employee by id")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "api to find employee by id", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
    @GetMapping("/employee/{id}")
    public UserDto getEmployee(@PathVariable String id) throws ResponseException {
        return userService.getEmployeeById(id);
    }
}
