package org.licesys.license.service;

import org.licesys.common.entities.Owner;
import org.licesys.license.command.RegisterOwnerCommand;
import org.licesys.license.command.UpdateOwnerCommand;

public interface OwnerService extends GenericService<Owner, Long> {

    void register(final RegisterOwnerCommand command);

    void update(final String idCard, final UpdateOwnerCommand command);
}
