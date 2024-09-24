package org.licesys.license.events;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public abstract class AbstractEventHandler {

    protected final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String message, int partition) {
        kafkaTemplate.send(getTopicName(), partition, null, message);
    }

    protected abstract String getTopicName();
}
