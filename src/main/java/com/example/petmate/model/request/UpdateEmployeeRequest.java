package com.example.petmate.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeRequest {

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String dateOfBirth;
}
