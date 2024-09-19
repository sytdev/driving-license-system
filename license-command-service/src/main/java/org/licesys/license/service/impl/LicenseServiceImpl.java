package org.licesys.license.service.impl;

import org.licesys.common.entities.*;
import org.licesys.license.command.IssueLicenseCommand;
import org.licesys.license.command.UpdateLicenseCommand;
import org.licesys.license.exception.BusinessRuleException;
import org.licesys.license.exception.ResourceNotFoundException;
import org.licesys.license.repository.LicenseRepository;
import org.licesys.license.repository.OwnerRepository;
import org.licesys.license.service.LicenseService;
import org.licesys.license.service.converter.LicenseConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;


@Service
public class LicenseServiceImpl extends GenericServiceImpl<License, Long> implements LicenseService {

    private final LicenseRepository licenseRepository;
    private final OwnerRepository ownerRepository;
    private final LicenseConverter converter;

    public LicenseServiceImpl(final LicenseRepository licenseRepository, final OwnerRepository ownerRepository,
                              final LicenseConverter converter) {
        super(licenseRepository);
        this.licenseRepository = licenseRepository;
        this.ownerRepository = ownerRepository;
        this.converter = converter;
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

        licenseRepository.save(converter.toRegisterLicenseEntity(owner, command));
    }

    @Override
    public void update(final String licenseNumber, final UpdateLicenseCommand command) {

        License license = licenseRepository.findByLicenseNumber(licenseNumber)
                                .orElseThrow(() -> new ResourceNotFoundException("License not found"));

        licenseRepository.save(converter.toUpdateLicenseEntity(license, command));
    }

    @Override
    @Transactional
    public void invalidate(final String licenseNumber) {

        License license = licenseRepository.findByLicenseNumber(licenseNumber)
                                .orElseThrow(() -> new ResourceNotFoundException("License not found"));

        LocalDate currentDate = LocalDate.now();
        LocalDate lastDateToRenew = license.getExpirationDate().plusDays(30);

        if(currentDate.isBefore(lastDateToRenew) || currentDate.isEqual(lastDateToRenew)) {
            throw new BusinessRuleException("License is expired and still valid, " +
                    "but must be renewed before " + lastDateToRenew);
        }
        licenseRepository.invalidate(licenseNumber);
    }

}
