package org.licesys.license.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import static org.licesys.license.validator.RequestValidator.*;

public record UpdateOwnerCommand(
        @NotBlank(message = "First Name cannot be empty") @Size(min = 1, max = 64, message = "First Name must be less than 64 characters") String firstName,
        @NotBlank(message = "Last Name cannot be empty") @Size(min = 1, max = 64, message = "Last Name must be less than 64 characters") String lastName,
        @Positive(message = "Age should be positive") Integer age) {

    public UpdateOwnerCommand(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        validate(this);
    }


}
