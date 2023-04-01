package com.example.petmate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import nonapi.io.github.classgraph.json.Id;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@MappedSuperclass
@SuperBuilder(toBuilder = true)
public abstract class BaseEntity {
	@Id
	@GeneratedValue
	private UUID id;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	private void insert() {
		createdAt = updatedAt = LocalDateTime.now(ZoneOffset.UTC);
	}

	@PreUpdate
	private void update() {
		updatedAt = LocalDateTime.now(ZoneOffset.UTC);
	}
}
