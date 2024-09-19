package org.licesys.license.query;

public record FilterLicensesQuery(
        String status,
        String type,
        String licenseNumber
) {
}
