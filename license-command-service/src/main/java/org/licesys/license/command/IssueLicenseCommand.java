package org.licesys.license.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;
import org.licesys.common.entities.Owner;

import static org.licesys.license.validator.RequestValidator.validate;

public record IssueLicenseCommand(
        @NotBlank(message = "Owner's IdCard field cannot be null")
        @Size(min = 8, max = 12, message = "ID Card must be between 8 and 12 characters")
        String ownerIdCard,

        @NotBlank(message = "Type field cannot be null")
        @Pattern(regexp = "A1|A2|A3", message = "Only A1, A2 and A3 are available")
        String type,

        @NotNull
        @Positive(message = "Expiration period must be positive")
        @Range(min = 1, max = 3, message = "Expiration period allows 1, 2 or 3 months")
        Integer expirationPeriod) {

    public IssueLicenseCommand(String ownerIdCard, String type, Integer expirationPeriod) {
        this.ownerIdCard = ownerIdCard;
        this.type = type;
        this.expirationPeriod = expirationPeriod;
        validate(this);
    }
}

//public record IssueLicenseCommand(
//        @Valid OwnerInfoCommand owner,
//        @NotBlank(message = "Type field cannot be null")
//        @Pattern(regexp = "A1|A2|A3", message = "Only A1, A2 and A3 are available") String type) {
//
//    public IssueLicenseCommand(OwnerInfoCommand owner, String type) {
//        this.owner = owner;
//        this.type = type;
//        validate(this);
//    }
//}
