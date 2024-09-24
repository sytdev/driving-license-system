package org.licesys.license.service;

import lombok.RequiredArgsConstructor;
import org.licesys.common.documents.License;
import org.licesys.license.dto.DisplayLicenseValidityDTO;
import org.licesys.license.dto.DisplayLicensesDTO;
import org.licesys.license.dto.DisplayOwnerDTO;
import org.licesys.license.exception.ResourceNotFoundException;
import org.licesys.license.query.FilterLicensesQuery;
import org.licesys.license.repository.LicenseRepository;
import org.licesys.license.repository.LicenseTemplateRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;

    private final LicenseTemplateRepository licenseTemplateRepository;

    @Override
    public DisplayLicenseValidityDTO displayLicenseValidity(final String licenseNumber) {

        License l = licenseRepository.findLicenseByLicenseNumber(licenseNumber)
                                        .orElseThrow(() -> new ResourceNotFoundException("License not found"));

        return new DisplayLicenseValidityDTO(
                l.getLicenseNumber(),
                l.getExpirationDate(),
                l.getOwner().getIdCard());
    }

    @Override
    public List<DisplayLicensesDTO> displayLicenses(final FilterLicensesQuery query) {

        List<License> licenses = licenseTemplateRepository.findLicensesByFilters(query.status(), query.type(), query.licenseNumber());

        return licenses.stream().map(
                l -> new DisplayLicensesDTO(l.getLicenseNumber(), l.getType(),
                        l.getStatus(), l.getIssueDate(), l.getExpirationDate(),
                        new DisplayOwnerDTO(l.getOwner().getIdCard(),
                                l.getOwner().getFirstName() + " " + l.getOwner().getLastName())
                )).collect(toList());
    }
}
