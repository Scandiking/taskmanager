package com.nag.taskmanager.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonDTO {

    // Getters and setters
    // ID is not needed as it's auto-generated
    private Long id;


    @NotBlank(message="First name is required. Enter a first name.") // Exception handling for validation
    private String firstName;
    @NotBlank(message="Last name is required. Enter a last name.") // Exception handling for validation
    private String lastName;
    @NotBlank(message="Email is required. Enter an email.") // Exception handling for validation
    private String email;
    @NotBlank(message="Phone is required. Enter a phone number.") // Exception handling for validation
    private String phone;

    // Default constructor needed for JPA
    public PersonDTO() {

    }

    // Constructor with fields
    public PersonDTO(Long id, String firstName, String lastName, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;

    }


}
