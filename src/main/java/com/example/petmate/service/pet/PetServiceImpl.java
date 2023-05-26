package com.example.petmate.service.pet;

import com.example.petmate.constant.ResponseCodes;
import com.example.petmate.entity.Pet;
import com.example.petmate.entity.User;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.mapper.PetMapper;
import com.example.petmate.model.request.PetRequest;
import com.example.petmate.model.response.PetResponse;
import com.example.petmate.repository.PetRepository;
import com.example.petmate.repository.UserRepository;
import com.example.petmate.service.third_party.firebase.FirebaseStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    private final FirebaseStorageService firebaseStorageService;

    public PetServiceImpl(PetRepository petRepository, UserRepository userRepository,
            FirebaseStorageService firebaseStorageService) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.firebaseStorageService = firebaseStorageService;
    }

    @Override
    public PetResponse createPet(PetRequest request) throws ResponseException, IOException {
        String image = "";
        log.info(request.getName());
        if (!request.getImage().isEmpty()) {
            image = firebaseStorageService.uploadImage(request.getImage());
        }
        Pet pet = petRepository.save(PetMapper.toPetEntity(request,image));
        Optional<User> user = userRepository.findById(pet.getUserId());
        return PetMapper.toPetResponse(pet, user.get());
    }

    @Override
    public boolean updatePet(String petId, PetRequest request) {
        Optional<Pet> pet = petRepository.findById(UUID.fromString(petId));
        if (pet.isEmpty()) {
            throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
        }

        Optional<User> user = userRepository.findById(UUID.fromString(request.getOwner()));
        if (user.isEmpty()) {
            throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
        }

        pet.get().setName(request.getName());
        pet.get().setSpecies(request.getSpecies());
        pet.get().setBreed(request.getBreed());
        pet.get().setAge(request.getAge());
        pet.get().setGender(request.isGender());
        pet.get().setWeight(request.getWeight());
        petRepository.save((pet.get()));

        return true;
    }

    @Override
    public Pet getPetById(String petId) {
        Optional<Pet> pet = petRepository.findById(UUID.fromString(petId));

        if (pet.isEmpty()) {
            throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
        }

        return pet.get();
    }

    @Override
    public List<Pet> getAllPetsOfUser(String userId) {
        Optional<User> user = userRepository.findById(UUID.fromString(userId));
        if (user.isEmpty()) {
            throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
        }
        return petRepository.findByUserId(UUID.fromString(userId));
    }

    @Override
    public boolean deletePet(String petId) {
        Optional<Pet> pet = petRepository.findById(UUID.fromString(petId));

        if (pet.isEmpty()) {
            throw new ResponseException(ResponseCodes.PM_NOT_FOUND);
        }

        petRepository.deleteById(UUID.fromString(petId));
        return true;
    }
}
