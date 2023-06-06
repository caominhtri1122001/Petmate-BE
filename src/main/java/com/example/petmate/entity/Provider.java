package com.example.petmate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "providers")
public class Provider extends BaseEntity {
	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private float price;

	@Column(name = "sitter_id")
	private UUID sitterId;

	@Column(name = "disable")
	private boolean disable;
}
