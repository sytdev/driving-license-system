package org.licesys.license.command;

import jakarta.validation.constraints.Pattern;

import static org.licesys.license.validator.RequestValidator.validate;

public record UpdateLicenseCommand(
        //@Pattern(regexp = "A1|A2|A3", message = "Only A1, A2 or A3 are available") String type,
        OwnerInfoCommand owner,
        Integer expirationPeriod
) {

    public UpdateLicenseCommand(
            //String type,
            OwnerInfoCommand owner,
            Integer expirationPeriod) {
        //this.type = type;
        this.owner = owner;
        this.expirationPeriod = expirationPeriod;
        validate(this);
    }

}
