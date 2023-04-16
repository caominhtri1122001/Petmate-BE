package com.example.petmate.mapper;

import com.example.petmate.entity.Pet;
import com.example.petmate.entity.User;
import com.example.petmate.model.request.CreatePetRequest;
import com.example.petmate.model.response.CreatePetResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper
public interface PetMapper {
    static CreatePetResponse toPetResponse(Pet entity, User user) {
        return CreatePetResponse.builder()
                .name(entity.getName())
                .species(entity.getSpecies())
                .breed(entity.getBreed())
                .age(entity.getAge())
                .gender(entity.isGender())
                .weight(entity.getWeight())
                .owner(user.getEmail())
                .build();
    }

    static Pet toPetEntity(CreatePetRequest createPetRequest) {
        return Pet.builder()
                .name(createPetRequest.getName())
                .species(createPetRequest.getSpecies())
                .breed(createPetRequest.getBreed())
                .age(createPetRequest.getAge())
                .gender(createPetRequest.isGender())
                .weight(createPetRequest.getWeight())
                .userId(UUID.fromString(createPetRequest.getOwner()))
                .build();
    }
}
