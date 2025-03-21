package com.nag.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonDTO {

    // Getters and setters
    private Long id;

    @NotBlank(message="First name is required. Enter a first name.")
    private String firstName;
    @NotBlank(message="Last name is required. Enter a last name.")
    private String lastName;
    @NotBlank(message="Email is required. Enter an email.")
    private String email;
    @NotBlank(message="Phone is required. Enter a phone number.")
    private String phone;

    public PersonDTO() {

    }

    public PersonDTO(Long id, String firstName, String lastName, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;

    }


}
