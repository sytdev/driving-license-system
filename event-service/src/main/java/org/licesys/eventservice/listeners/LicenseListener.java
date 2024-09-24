package org.licesys.eventservice.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.licesys.common.documents.License;
import org.licesys.common.documents.Owner;
import org.licesys.common.model.events.LicenseEventModel;
import org.licesys.eventservice.repository.LicenseRepository;
import org.licesys.eventservice.repository.OwnerRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LicenseListener {

    private final ObjectMapper mapper;
    private final LicenseRepository licenseRepository;
    private final OwnerRepository ownerRepository;

    public LicenseListener(ObjectMapper mapper, LicenseRepository licenseRepository, OwnerRepository ownerRepository) {
        this.mapper = mapper;
        this.licenseRepository = licenseRepository;
        this.ownerRepository = ownerRepository;
    }

    @KafkaListener(topics = "${license.topic.name}", groupId = "${spring.kafka.consumer.group-id}",
            topicPartitions = @TopicPartition(topic = "${license.topic.name}", partitions = "${license.topic.partitions.issue-lcs}"))
    public void listenCreation(final String licenseJson) throws JsonProcessingException {
        log.info("Received license creation event: {}", licenseJson);

        LicenseEventModel licenseObject = mapper.readValue(licenseJson, LicenseEventModel.class);

        Owner owner = ownerRepository.findByIdCard(licenseObject.getOwnerIdCard());

        License license = new License();
        license.setLicenseNumber(licenseObject.getLicenseNumber());
        license.setOwner(owner);
        license.setIssueDate(licenseObject.getIssueDate());
        license.setExpirationDate(licenseObject.getExpirationDate());
        license.setType(licenseObject.getType());
        license.setStatus(licenseObject.getStatus());
        license.setCreatedBy(licenseObject.getCreatedBy());
        license.setCreatedAt(licenseObject.getCreatedAt());
        license.setModifiedBy(licenseObject.getModifiedBy());
        license.setModifiedAt(licenseObject.getModifiedAt());

        licenseRepository.save(license);
    }

    @KafkaListener(topics = "${license.topic.name}", groupId = "${spring.kafka.consumer.group-id}",
            topicPartitions = @TopicPartition(topic = "${license.topic.name}", partitions = "${license.topic.partitions.update-lcs}"))
    public void listenUpdate(final String licenseJson) throws JsonProcessingException {
        log.info("Received license update event: {}", licenseJson);

        LicenseEventModel licenseObject = mapper.readValue(licenseJson, LicenseEventModel.class);

        License license = licenseRepository.findByLicenseNumber(licenseObject.getLicenseNumber());

        Owner owner = ownerRepository.findByIdCard(licenseObject.getOwnerIdCard());
        owner.setFirstName(licenseObject.getOwnerFirstName());
        owner.setLastName(licenseObject.getOwnerLastName());
        owner.setAge(licenseObject.getAge());

        license.setExpirationDate(licenseObject.getExpirationDate());
        license.setModifiedBy(licenseObject.getModifiedBy());
        license.setModifiedAt(licenseObject.getModifiedAt());

        licenseRepository.save(license);
        ownerRepository.save(owner);
    }

    @KafkaListener(topics = "${license.topic.name}", groupId = "${spring.kafka.consumer.group-id}",
            topicPartitions = @TopicPartition(topic = "${license.topic.name}", partitions = "${license.topic.partitions.invalidate-lcs}"))
    public void listenInvalidation(final String licenseJson) throws JsonProcessingException {
        log.info("Received license invalidation event: {}", licenseJson);

        LicenseEventModel licenseObject = mapper.readValue(licenseJson, LicenseEventModel.class);

        License license = licenseRepository.findByLicenseNumber(licenseObject.getLicenseNumber());

        license.setStatus(licenseObject.getStatus());
        license.setModifiedBy(licenseObject.getModifiedBy());
        license.setModifiedAt(licenseObject.getModifiedAt());

        licenseRepository.save(license);
    }

}
