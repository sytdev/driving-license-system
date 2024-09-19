package org.licesys.license.service;

import org.licesys.license.dto.DisplayLicenseValidityDTO;
import org.licesys.license.dto.DisplayLicensesDTO;
import org.licesys.license.query.FilterLicensesQuery;

import java.util.List;

public interface LicenseService {

    DisplayLicenseValidityDTO displayLicenseValidity(final String licenseNumber);

    List<DisplayLicensesDTO> displayLicenses(final FilterLicensesQuery query);
}
