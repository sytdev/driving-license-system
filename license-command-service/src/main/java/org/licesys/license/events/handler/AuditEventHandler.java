package org.licesys.license.events.handler;

import org.licesys.license.events.AbstractEventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("auditEventHandler")
public class AuditEventHandler extends AbstractEventHandler {

    @Value("${audit.topic.name}")
    protected String topicName;

    public AuditEventHandler(KafkaTemplate<String, String> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    protected String getTopicName() {
        return topicName;
    }
}
