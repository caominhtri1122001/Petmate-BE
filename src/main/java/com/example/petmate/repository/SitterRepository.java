package com.example.petmate.repository;

import com.example.petmate.entity.Sitter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SitterRepository extends JpaRepository<Sitter, UUID> {
	Optional<Sitter> findByUserId(UUID id);
}
