package com.example.petmate.entity;

import com.example.petmate.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "date_of_birth")
	private LocalDateTime dateOfBirth;

	@Column(name = "user_role")
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Column(name = "user_img_url")
	private String userImgUrl;

	@Column(name = "gender")
	private boolean gender;

	@Column(name = "phone")
	private String phone;
}
