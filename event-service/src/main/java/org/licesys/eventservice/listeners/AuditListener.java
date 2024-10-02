package org.licesys.eventservice.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.licesys.common.constants.TargetPartition;
import org.licesys.common.documents.Audit;
import org.licesys.common.model.events.AuditEventModel;
import org.licesys.eventservice.repository.AuditRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuditListener {

    private final AuditRepository auditRepository;
    private final ObjectMapper mapper;

    public AuditListener(AuditRepository auditRepository, ObjectMapper mapper) {
        this.auditRepository = auditRepository;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "${audit.topic.name}", groupId = "event-group",
            topicPartitions = @TopicPartition(topic = "${audit.topic.name}", partitions = {"${audit.topic.partitions.save-adt}"}))
    public void listenCreation(final String auditJson) throws JsonProcessingException {
        log.info("Received Audit event: {}", auditJson);
        AuditEventModel auditEventModel = mapper.readValue(auditJson, AuditEventModel.class);

        Audit audit = new Audit();
        audit.setAction(auditEventModel.getAction());
        audit.setOperationDate(auditEventModel.getOperationDate());
        audit.setUsername(auditEventModel.getUsername());
        audit.setUserFullName(auditEventModel.getUserFullName());

        auditRepository.save(audit);
    }
}
