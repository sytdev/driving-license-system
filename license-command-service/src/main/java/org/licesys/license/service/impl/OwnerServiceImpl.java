package org.licesys.license.service.impl;

import org.licesys.common.entities.Audit;
import org.licesys.common.entities.Owner;
import org.licesys.license.command.RegisterOwnerCommand;
import org.licesys.license.command.UpdateOwnerCommand;
import org.licesys.license.events.AbstractEventHandler;
import org.licesys.license.exception.BusinessRuleException;
import org.licesys.license.exception.ResourceNotFoundException;
import org.licesys.license.filters.utils.UserContextHolder;
import org.licesys.license.repository.OwnerRepository;
import org.licesys.license.service.OwnerService;
import org.licesys.license.service.converter.AuditConverter;
import org.licesys.license.service.converter.OwnerConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.licesys.common.constants.TargetPartition.*;

//TODO validate transactionality
@Service
public class OwnerServiceImpl extends GenericServiceImpl<Owner, Long> implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final AbstractEventHandler ownerEventHandler;
    private final AbstractEventHandler auditEventHandler;
    private final OwnerConverter ownerConverter;
    private final AuditConverter auditConverter;

    public OwnerServiceImpl(final OwnerRepository ownerRepository, @Qualifier("ownerEventHandler") AbstractEventHandler ownerEventHandler,
                            @Qualifier("auditEventHandler") AbstractEventHandler auditEventHandler, OwnerConverter ownerConverter, AuditConverter auditConverter) {
        super(ownerRepository);
        this.ownerRepository = ownerRepository;
        this.ownerEventHandler = ownerEventHandler;
        this.auditEventHandler = auditEventHandler;
        this.ownerConverter = ownerConverter;
        this.auditConverter = auditConverter;
    }

    @Override
    public void register(final RegisterOwnerCommand command) {

        Optional<Owner> owner = ownerRepository.findOwnerByIdCard(command.idCard());

        if (owner.isPresent()){
            throw new BusinessRuleException("There's an existing owner with the same id card");
        }

        Owner newOwner = new Owner();
        newOwner.setIdCard(command.idCard());
        newOwner.setFirstName(command.firstName());
        newOwner.setLastName(command.lastName());
        newOwner.setAge(command.age());
        newOwner.setAudit(new Audit(UserContextHolder.getContext().getUsername()));

        save(newOwner);
        ownerEventHandler.send(ownerConverter.toJson(newOwner), PARTITION_REGISTER_OWNER);
        auditEventHandler.send(auditConverter.toJson(newOwner.getAudit(), "An owner was created"), PARTITION_SAVE_AUDIT);
    }

    @Override
    public void update(final String idCard, final UpdateOwnerCommand command) {

        Owner owner = ownerRepository.findOwnerByIdCard(idCard)
                                        .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        owner.setFirstName(command.firstName());
        owner.setLastName(command.lastName());
        owner.setAge(command.age());
        owner.getAudit().setModifiedBy(UserContextHolder.getContext().getUsername());
        owner = ownerRepository.save(owner);

        ownerEventHandler.send(ownerConverter.toJson(owner), PARTITION_UPDATE_OWNER);
        auditEventHandler.send(auditConverter.toJson(owner.getAudit(), "An owner was modified"), PARTITION_SAVE_AUDIT);
    }

    //overriding save method from generic class
    @Override
    public void save(Owner owner) {
        ownerRepository.save(owner);
    }

}
