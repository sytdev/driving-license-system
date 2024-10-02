package org.licesys.license.service.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.licesys.common.entities.*;
import org.licesys.license.command.IssueLicenseCommand;
import org.licesys.license.command.UpdateLicenseCommand;
import org.licesys.common.model.events.LicenseEventModel;
import org.licesys.license.filters.utils.UserContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Component
public class LicenseConverter {

    private final ObjectMapper objectMapper;

    public LicenseConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public License toUpdateLicenseEntity(final License license, final UpdateLicenseCommand command) {

        license.setExpirationDate(command.expirationPeriod() != null
                ? license.getIssueDate().plusMonths(command.expirationPeriod()) : license.getExpirationDate());

        Owner owner = license.getOwner();

        owner.setFirstName(StringUtils.hasText(command.owner().firstName())
                ? command.owner().firstName() : license.getOwner().getFirstName());

        owner.setLastName(StringUtils.hasText(command.owner().lastName())
                ? command.owner().lastName() : license.getOwner().getLastName());

        owner.setAge(command.owner().age() != null
                ? command.owner().age() : license.getOwner().getAge());
        owner.getAudit().setModifiedBy(UserContextHolder.getContext().getUsername());
        license.setOwner(owner);

        license.getAudit().setModifiedBy(UserContextHolder.getContext().getUsername());
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
        license.setAudit(new Audit(UserContextHolder.getContext().getUsername()));

        return license;

    }

    public String toJson(final License license){

        LicenseEventModel eventModel = new LicenseEventModel();
        eventModel.setLicenseNumber(license.getLicenseNumber());
        eventModel.setIssueDate(license.getIssueDate());
        eventModel.setExpirationDate(license.getExpirationDate());
        eventModel.setStatus(license.getStatus().toString());
        eventModel.setType(license.getType().toString());
        eventModel.setOwnerIdCard(license.getOwner().getIdCard());
        eventModel.setOwnerFirstName(license.getOwner().getFirstName());
        eventModel.setOwnerLastName(license.getOwner().getLastName());
        eventModel.setAge(license.getOwner().getAge());
        eventModel.setCreatedBy(license.getAudit().getCreatedBy());
        eventModel.setCreatedAt(license.getAudit().getCreatedAt());
        eventModel.setModifiedBy(license.getAudit().getLastModifiedBy());
        eventModel.setModifiedAt(license.getAudit().getLastModifiedAt());

        try{
            return objectMapper.writeValueAsString(eventModel);
        }catch (IOException e){
            log.error("Error while writing to JSON", e);
            throw new RuntimeException("There is an error while writing to the json output", e);
        }
    }
}
