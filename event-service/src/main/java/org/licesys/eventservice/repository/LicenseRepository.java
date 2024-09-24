package org.licesys.eventservice.repository;

import org.licesys.common.documents.License;

public interface LicenseRepository extends GenericRepository<License, String> {

    License findByLicenseNumber(String licenseNumber);

}
