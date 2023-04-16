package com.example.petmate.entity;

import com.example.petmate.constant.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "species")
    private String species;

    @Column(name = "breed")
    private String breed;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "weight")
    private int weight;

    @Column(name = "pet_img_url")
    private String petImgUrl;

    @Column(name = "user_id")
    private UUID userId;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({ "id", "createdAt", "updatedAt", "password", "userImgUrl", "dateOfBirth" })
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User owner;
}
