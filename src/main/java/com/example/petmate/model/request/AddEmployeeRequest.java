package com.example.petmate.model.request;

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
public class AddEmployeeRequest {
    private String firstName;

    private String lastName;

    private String emailAddress;

    private String password;

    private String dateOfBirth;
}
