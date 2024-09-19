package org.licesys.license.service.converter;

import org.licesys.common.entities.*;
import org.licesys.license.command.IssueLicenseCommand;
import org.licesys.license.command.UpdateLicenseCommand;
import org.licesys.license.service.impl.LicenseServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class LicenseConverter {

    public License toUpdateLicenseEntity(final License license, final UpdateLicenseCommand command) {

//        license.setType(StringUtils.hasText(command.type())
//                ? LicenseType.valueOf(command.type()) : license.getType());

        license.setExpirationDate(command.expirationPeriod() != null
                ? license.getIssueDate().plusMonths(command.expirationPeriod()) : license.getExpirationDate());

        Owner owner = license.getOwner();

        owner.setFirstName(StringUtils.hasText(command.owner().firstName())
                ? command.owner().firstName() : license.getOwner().getFirstName());

        owner.setLastName(StringUtils.hasText(command.owner().lastName())
                ? command.owner().lastName() : license.getOwner().getLastName());

        owner.setAge(command.owner().age() != null
                ? command.owner().age() : license.getOwner().getAge());
        owner.getAudit().setModifiedBy("TEST4");
        license.setOwner(owner);

        license.getAudit().setModifiedBy("TEST3");
        return license;
    }

    public License toRegisterLicenseEntity(final Owner owner,final IssueLicenseCommand command) {

        License license = new License();
        license.setLicenseNumber(UUID.randomUUID().toString());
        license.setType(LicenseType.valueOf(command.type()));
        license.setIssueDate(LocalDate.now());
        license.setExpirationDate(LocalDate.now().plusMonths(command.expirationPeriod()));
        license.setStatus(Status.ACTIVE);
        license.setOwner(owner);
        license.setAudit(new Audit("SYSTEM"));

        return license;

    }
}
