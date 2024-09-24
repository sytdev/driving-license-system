package org.licesys.license.events.handler;

import org.licesys.license.events.AbstractEventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("ownerEventHandler")
public class OwnerEventHandler extends AbstractEventHandler {

    @Value("${owner.topic.name}")
    private String topicName;

    public OwnerEventHandler(KafkaTemplate<String, String> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    protected String getTopicName() {
        return topicName;
    }
}

