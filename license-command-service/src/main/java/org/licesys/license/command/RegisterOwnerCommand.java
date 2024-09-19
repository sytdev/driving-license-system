package org.licesys.license.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import static org.licesys.license.validator.RequestValidator.*;

public record RegisterOwnerCommand(
        @NotBlank @Size(min = 8, max = 12, message = "ID Card must be between 8 and 12 characters") String idCard,
        @NotBlank @Size(min = 1, max = 64, message = "First Name must be less than 64 characters") String firstName,
        @NotBlank @Size(min = 1, max = 64, message = "Last Name must be less than 64 characters") String lastName,
        @Positive(message = "Age should be positive") Integer age) {

    public RegisterOwnerCommand(String idCard, String firstName, String lastName, Integer age) {
        this.idCard = idCard;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        validate(this);
    }
}
