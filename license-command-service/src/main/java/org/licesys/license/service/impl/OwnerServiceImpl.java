package org.licesys.license.service.impl;

import org.licesys.common.entities.Audit;
import org.licesys.common.entities.Owner;
import org.licesys.license.command.RegisterOwnerCommand;
import org.licesys.license.command.UpdateOwnerCommand;
import org.licesys.license.exception.ResourceNotFoundException;
import org.licesys.license.repository.LicenseRepository;
import org.licesys.license.repository.OwnerRepository;
import org.licesys.license.service.OwnerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OwnerServiceImpl extends GenericServiceImpl<Owner, Long> implements OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(final OwnerRepository ownerRepository) {
        super(ownerRepository);
        this.ownerRepository = ownerRepository;
    }

    @Override
    public void register(final RegisterOwnerCommand command) {

        Owner owner = new Owner();
        owner.setIdCard(command.idCard());
        owner.setFirstName(command.firstName());
        owner.setLastName(command.lastName());
        owner.setAge(command.age());
        owner.setAudit(new Audit("SYSTEM"));

        save(owner);
    }

    @Override
    public void update(final String idCard, final UpdateOwnerCommand command) {

        Owner owner = ownerRepository.findOwnerByIdCard(idCard)
                                        .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        owner.setFirstName(command.firstName());
        owner.setLastName(command.lastName());
        owner.setAge(command.age());
        owner.getAudit().setModifiedBy("TEST");
        ownerRepository.save(owner);
    }

    @Override
    public void save(Owner owner) {
        ownerRepository.save(owner);
    }

}
