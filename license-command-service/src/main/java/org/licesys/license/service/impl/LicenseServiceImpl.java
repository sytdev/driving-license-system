package org.licesys.license.service.impl;

import org.licesys.common.entities.*;
import org.licesys.license.command.IssueLicenseCommand;
import org.licesys.license.command.UpdateLicenseCommand;
import org.licesys.license.events.AbstractEventHandler;
import org.licesys.license.events.handler.LicenseEventHandler;
import org.licesys.license.exception.BusinessRuleException;
import org.licesys.license.exception.ResourceNotFoundException;
import org.licesys.license.filters.utils.UserContextHolder;
import org.licesys.license.repository.LicenseRepository;
import org.licesys.license.repository.OwnerRepository;
import org.licesys.license.service.LicenseService;
import org.licesys.license.service.converter.AuditConverter;
import org.licesys.license.service.converter.LicenseConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.licesys.common.constants.TargetPartition.*;


@Service
public class LicenseServiceImpl extends GenericServiceImpl<License, Long> implements LicenseService {

    private final LicenseRepository licenseRepository;
    private final OwnerRepository ownerRepository;
    private final LicenseConverter converter;
    private final AuditConverter auditConverter;
    private final AbstractEventHandler auditEventHandler;
    private final AbstractEventHandler cqrsEventHandler;
    private final LicenseEventHandler licenseEventHandler;

    public LicenseServiceImpl(final LicenseRepository licenseRepository, final OwnerRepository ownerRepository,
                              final LicenseConverter converter, AuditConverter auditConverter, @Qualifier("auditEventHandler") final AbstractEventHandler auditEventHandler,
                              @Qualifier("licenseEventHandler") final AbstractEventHandler cqrsEventHandler, LicenseEventHandler licenseEventHandler) {
        super(licenseRepository);
        this.licenseRepository = licenseRepository;
        this.ownerRepository = ownerRepository;
        this.converter = converter;
        this.auditConverter = auditConverter;
        this.auditEventHandler = auditEventHandler;
        this.cqrsEventHandler = cqrsEventHandler;
        this.licenseEventHandler = licenseEventHandler;
    }

    @Override
    public void issue(final IssueLicenseCommand command) {

        Owner owner = ownerRepository.findOwnerByIdCard(command.ownerIdCard())
                .orElseThrow(() -> new ResourceNotFoundException("Owner's Id Card not found"));

        License license = licenseRepository.findByTypeAndOwnerIdCard(LicenseType.valueOf(command.type()),
                command.ownerIdCard()).orElse(null);

        if (license != null) {
            throw new BusinessRuleException("License with the same type and id card already exists");
        }

        license = licenseRepository.save(converter.toRegisterLicenseEntity(owner, command));

        cqrsEventHandler.send(converter.toJson(license), PARTITION_ISSUE_LICENSE);
        auditEventHandler.send(auditConverter.toJson(license.getAudit(), "A license was issued"), PARTITION_SAVE_AUDIT);
    }

    @Override
    public void update(final String licenseNumber, final UpdateLicenseCommand command) {

        License license = licenseRepository.findByLicenseNumber(licenseNumber)
                                .orElseThrow(() -> new ResourceNotFoundException("License not found"));

        license = licenseRepository.save(converter.toUpdateLicenseEntity(license, command));

        cqrsEventHandler.send(converter.toJson(license), PARTITION_UPDATE_LICENSE);
        auditEventHandler.send(auditConverter.toJson(license.getAudit(), "A License was updated"), PARTITION_SAVE_AUDIT);
    }

    @Override
    @Transactional
    public void invalidate(final String licenseNumber) {

        License license = licenseRepository.findByLicenseNumber(licenseNumber)
                                .orElseThrow(() -> new ResourceNotFoundException("License not found"));

        LocalDate currentDate = LocalDate.now();
        LocalDate lastDateToRenew = license.getExpirationDate().plusDays(30);

        if(currentDate.isBefore(lastDateToRenew) || currentDate.isEqual(lastDateToRenew)) {
            throw new BusinessRuleException("License is still valid, " +
                    "but must be renewed before " + lastDateToRenew);
        }
        //licenseRepository.invalidate(licenseNumber);
        license.setStatus(Status.NOT_RENEWED);
        license.getAudit().setModifiedBy(UserContextHolder.getContext().getUsername());
        licenseRepository.save(license);

        cqrsEventHandler.send(converter.toJson(license), PARTITION_INVALIDATE_LICENSE);
        auditEventHandler.send(auditConverter.toJson(license.getAudit(), "A license was invalidated"), PARTITION_SAVE_AUDIT);
    }

}
