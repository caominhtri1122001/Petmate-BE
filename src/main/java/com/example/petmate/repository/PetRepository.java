package com.example.petmate.repository;

import com.example.petmate.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet, UUID> {

    @Query(nativeQuery = true, value = """
            SELECT * FROM pets WHERE user_id = :userId
            """)
    List<Pet> findByUserId(@Param("userId") UUID userId);
}
