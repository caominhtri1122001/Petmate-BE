package com.example.petmate.repository;

import com.example.petmate.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet, UUID> {
    Pet findById(String petId);
}
