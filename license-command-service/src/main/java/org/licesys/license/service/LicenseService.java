package org.licesys.license.service;

import org.licesys.common.entities.License;
import org.licesys.license.command.IssueLicenseCommand;
import org.licesys.license.command.UpdateLicenseCommand;

public interface LicenseService extends GenericService<License, Long> {

    void issue(final IssueLicenseCommand command);

    void update(final String licenseNumber, final UpdateLicenseCommand command);

    void invalidate(String licenseNumber);
}
