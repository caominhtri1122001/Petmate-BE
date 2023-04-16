package com.example.petmate.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreatePetResponse {
    private String name;
    private String species;
    private String breed;
    private int age;
    private boolean gender;
    private int weight;
    private String owner;
}
