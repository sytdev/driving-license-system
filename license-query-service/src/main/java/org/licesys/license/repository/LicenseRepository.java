package org.licesys.license.repository;

import org.licesys.common.documents.License;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LicenseRepository extends GenericRepository<License, String> {

    Optional<License> findLicenseByLicenseNumber(final String licenseNumber);


    //List<License> findAllLicenses(final String status, final String type, final String licenseNumber);
}
