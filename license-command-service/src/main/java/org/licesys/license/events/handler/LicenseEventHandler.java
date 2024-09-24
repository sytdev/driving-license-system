package org.licesys.license.events.handler;

import org.licesys.license.events.AbstractEventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("licenseEventHandler")
public class LicenseEventHandler extends AbstractEventHandler {

    @Value("${license.topic.name}")
    private String topicName;

    public LicenseEventHandler(KafkaTemplate<String, String> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    protected String getTopicName() {
        return topicName;
    }
}
