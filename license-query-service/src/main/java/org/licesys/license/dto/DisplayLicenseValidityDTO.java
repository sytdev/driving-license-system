package org.licesys.license.dto;

import java.time.LocalDate;

public record DisplayLicenseValidityDTO(
        String licenseNumber,
        LocalDate expirationDate,
        String status,
        String ownerIdCard
) {
}
