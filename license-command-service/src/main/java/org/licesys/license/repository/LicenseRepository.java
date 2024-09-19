package org.licesys.license.repository;

import org.licesys.common.entities.License;
import org.licesys.common.entities.LicenseType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LicenseRepository extends GenericRepository<License, Long> {

    Optional<License> findByLicenseNumber(final String licenseNumber);

    @Query("select l from License l inner join Owner o on l.owner.id = o.id " +
                        "where l.type = :type and o.idCard = :ownerIdCard")
    Optional<License> findByTypeAndOwnerIdCard(final LicenseType type, final String ownerIdCard);

    @Modifying
    @Query("update License l set l.status = org.licesys.common.entities.Status.NOT_RENEWED" +
                        " where l.licenseNumber = :licenseNumber")
    void invalidate(final String licenseNumber);
}
