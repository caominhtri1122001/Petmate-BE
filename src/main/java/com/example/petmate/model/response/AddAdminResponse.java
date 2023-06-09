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
public class AddAdminResponse {
    private String firstName;

    private String lastName;

    private String emailAddress;

    private String dateOfBirth;

    private boolean gender;

    private String phone;
}
