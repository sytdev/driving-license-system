package org.licesys.license.service.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.licesys.common.entities.Audit;
import org.licesys.common.model.events.AuditEventModel;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
public class AuditConverter{

    private final ObjectMapper objectMapper;

    public AuditConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String toJson(Audit audit, String action) {
        AuditEventModel auditEventModel = new AuditEventModel();
        auditEventModel.setAction(action);
        auditEventModel.setOperationDate(LocalDateTime.now());
        auditEventModel.setUsername("jdoe");
        auditEventModel.setUserFullName("John Doe");

        try {
            return objectMapper.writeValueAsString(auditEventModel);
        }catch (IOException e){
            log.error("Error while writing to JSON", e);
            throw new RuntimeException("There is an error while writing to the json output", e);
        }
    }
}
