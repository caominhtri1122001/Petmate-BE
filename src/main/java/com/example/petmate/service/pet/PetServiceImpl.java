package com.example.petmate.service.pet;

import com.example.petmate.entity.Pet;
import com.example.petmate.entity.User;
import com.example.petmate.exception.ResponseException;
import com.example.petmate.mapper.PetMapper;
import com.example.petmate.model.request.CreatePetRequest;
import com.example.petmate.model.response.CreatePetResponse;
import com.example.petmate.repository.PetRepository;
import com.example.petmate.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PetServiceImpl implements PetService{
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public PetServiceImpl(PetRepository petRepository, UserRepository userRepository) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CreatePetResponse petCreate(CreatePetRequest request) throws ResponseException {
        Pet pet = petRepository.save(PetMapper.toPetEntity(request));
        Optional<User> user = userRepository.findById(pet.getUserId());
        return PetMapper.toPetResponse(pet, user.get());
    }
}
