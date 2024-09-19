package org.licesys.license.command;

import jakarta.validation.constraints.Positive;

import static org.licesys.license.validator.RequestValidator.validate;

public record OwnerInfoCommand(
        String idCard,
        String firstName,
        String lastName,
        @Positive(message = "Age must be positive") Integer age) {

    public OwnerInfoCommand(String idCard, String firstName, String lastName, Integer age) {
        this.idCard = idCard;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        validate(this);
    }
}
