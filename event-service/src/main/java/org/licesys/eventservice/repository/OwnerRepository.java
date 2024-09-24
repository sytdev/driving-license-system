package org.licesys.eventservice.repository;

import org.licesys.common.documents.Owner;

public interface OwnerRepository extends GenericRepository<Owner, String> {

    //TODO create an index for IdCard field
    Owner findByIdCard(String idCard);
}
