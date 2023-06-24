package com.example.petmate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request extends BaseEntity{
	@Column(name = "start_date")
	private LocalDate startDate;

	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "start_time")
	private LocalTime startTime;

	@Column(name = "end_time")
	private LocalTime endTime;

	@Column(name = "message")
	private String message;

	@Column(name = "address")
	private String address;

	@Column(name = "status")
	private String status;

	@Column(name = "price")
	private float price;

	@Column(name = "valid")
	private boolean valid;

	@Column(name = "user_id")
	private UUID userId;

	@Column(name = "sitter_id")
	private UUID sitterId;

	@Column(name = "pet_id")
	private UUID petId;

	@Column(name = "service_id")
	private UUID serviceId;
}
