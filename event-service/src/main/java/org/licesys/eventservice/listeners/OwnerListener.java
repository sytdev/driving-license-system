package org.licesys.eventservice.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.licesys.common.documents.Owner;
import org.licesys.common.model.events.OwnerEventModel;
import org.licesys.eventservice.repository.OwnerRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OwnerListener {

    private final ObjectMapper mapper;
    private final OwnerRepository ownerRepository;

    public OwnerListener(ObjectMapper mapper, OwnerRepository ownerRepository) {
        this.mapper = mapper;
        this.ownerRepository = ownerRepository;
    }

    @KafkaListener(topics = "${owner.topic.name}", groupId = "${spring.kafka.consumer.group-id}",
            topicPartitions = @TopicPartition(topic = "${owner.topic.name}", partitions = "${owner.topic.partitions.register-owr}"))
    public void listenCreation(final String ownerJson) throws JsonProcessingException {
        log.info("Received owner creation event: {}", ownerJson);
        OwnerEventModel eventModel = mapper.readValue(ownerJson, OwnerEventModel.class);

        Owner owner = new Owner();
        owner.setIdCard(eventModel.getIdCard());
        owner.setFirstName(eventModel.getFirstName());
        owner.setLastName(eventModel.getLastName());
        owner.setAge(eventModel.getAge());
        owner.setCreatedBy(eventModel.getCreatedBy());
        owner.setCreatedAt(eventModel.getCreatedAt());
        owner.setModifiedBy(eventModel.getModifiedBy());
        owner.setModifiedAt(eventModel.getModifiedAt());
        ownerRepository.save(owner);
    }

    @KafkaListener(topics = "${owner.topic.name}", groupId = "${spring.kafka.consumer.group-id}",
            topicPartitions = @TopicPartition(topic = "${owner.topic.name}", partitions = "${owner.topic.partitions.update-owr}"))
    public void listenUpdate(final String ownerJson) throws JsonProcessingException {
        log.info("Received owner update event: {}", ownerJson);
        OwnerEventModel eventModel = mapper.readValue(ownerJson, OwnerEventModel.class);

        Owner owner = ownerRepository.findByIdCard(eventModel.getIdCard());
        owner.setFirstName(eventModel.getFirstName());
        owner.setLastName(eventModel.getLastName());
        owner.setAge(eventModel.getAge());
        owner.setModifiedBy(eventModel.getModifiedBy());
        owner.setModifiedAt(eventModel.getModifiedAt());

        ownerRepository.save(owner);
    }
}
