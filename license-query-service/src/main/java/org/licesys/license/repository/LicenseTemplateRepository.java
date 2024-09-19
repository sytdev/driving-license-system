package org.licesys.license.repository;

import org.licesys.common.documents.License;

import java.util.List;

public interface LicenseTemplateRepository {

    List<License> findLicensesByFilters(final String status, final String type, final String licenseNumber);
}
