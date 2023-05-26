package com.example.petmate.mapper;

import com.example.petmate.entity.Pet;
import com.example.petmate.entity.User;
import com.example.petmate.model.request.PetRequest;
import com.example.petmate.model.response.PetResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper
public interface PetMapper {
    static PetResponse toPetResponse(Pet entity, User user) {
        return PetResponse.builder()
                .name(entity.getName())
                .species(entity.getSpecies())
                .breed(entity.getBreed())
                .age(entity.getAge())
                .gender(entity.isGender())
                .weight(entity.getWeight())
                .image(entity.getPetImgUrl())
                .owner(user.getEmail())
                .build();
    }

    static Pet toPetEntity(PetRequest petRequest, String image) {
        return Pet.builder()
                .name(petRequest.getName())
                .species(petRequest.getSpecies())
                .breed(petRequest.getBreed())
                .age(petRequest.getAge())
                .gender(petRequest.isGender())
                .weight(petRequest.getWeight())
                .petImgUrl(image)
                .userId(UUID.fromString(petRequest.getOwner()))
                .build();
    }
}
