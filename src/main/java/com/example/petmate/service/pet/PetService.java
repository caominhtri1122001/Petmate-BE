package com.example.petmate.service.pet;

import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.CreatePetRequest;
import com.example.petmate.model.response.CreatePetResponse;

public interface PetService {
    CreatePetResponse petCreate(CreatePetRequest request) throws ResponseException;
}
