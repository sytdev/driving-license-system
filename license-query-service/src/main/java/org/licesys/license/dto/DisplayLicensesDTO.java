package org.licesys.license.dto;

import java.time.LocalDate;

public record DisplayLicensesDTO(
        String licenseNumber,
        String type,
        String status,
        LocalDate issuedDate,
        LocalDate expirationDate,
        DisplayOwnerDTO owner) {
}
