package org.licesys.license.command;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Range;

import static org.licesys.license.validator.RequestValidator.validate;

public record UpdateLicenseCommand(
        //@Pattern(regexp = "A1|A2|A3", message = "Only A1, A2 or A3 are available") String type,
        OwnerInfoCommand owner,
        @Range(min = 1, max = 3)
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
