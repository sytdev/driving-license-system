package org.licesys.license.repository;

import org.licesys.common.entities.Owner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OwnerRepository extends GenericRepository<Owner, Long> {

    @Query("select o from Owner o where o.idCard = :idCard")
    Optional<Owner> findOwnerByIdCard(@Param("idCard") String idCard);
}
