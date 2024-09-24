package org.licesys.license.service.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.licesys.common.entities.Audit;
import org.licesys.common.entities.Owner;
import org.licesys.common.model.events.AuditEventModel;
import org.licesys.common.model.events.OwnerEventModel;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
public class OwnerConverter {

    private final ObjectMapper objectMapper;

    public OwnerConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String toJson(Owner owner) {
        OwnerEventModel ownerEventModel = new OwnerEventModel();
        ownerEventModel.setIdCard(owner.getIdCard());
        ownerEventModel.setFirstName(owner.getFirstName());
        ownerEventModel.setLastName(owner.getLastName());
        ownerEventModel.setAge(owner.getAge());
        ownerEventModel.setCreatedBy(owner.getAudit().getCreatedBy());
        ownerEventModel.setCreatedAt(owner.getAudit().getCreatedAt());
        ownerEventModel.setModifiedBy(owner.getAudit().getLastModifiedBy());
        ownerEventModel.setModifiedAt(owner.getAudit().getLastModifiedAt());

        try {
            return objectMapper.writeValueAsString(ownerEventModel);
        }catch (IOException e){
            log.error("Error while writing to JSON", e);
            throw new RuntimeException("There is an error while writing to the json output", e);
        }
    }
}
