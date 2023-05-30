package com.example.petmate.repository;

import com.example.petmate.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, UUID> {
	Optional<List<Provider>> findBySitterId(UUID sitterId);
}
