package com.example.petmate.service.pet;

import com.example.petmate.entity.Pet;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.model.request.PetRequest;
import com.example.petmate.model.response.PetResponse;

import java.io.IOException;
import java.util.List;

public interface PetService {
    PetResponse createPet(PetRequest request) throws ResponseException, IOException;

    boolean updatePet(String petId, PetRequest request) throws IOException;

    Pet getPetById(String petId);

    List<Pet> getAllPetsOfUser(String userId);

    boolean deletePet(String petId);

    List<Pet> getAllPets();
}
