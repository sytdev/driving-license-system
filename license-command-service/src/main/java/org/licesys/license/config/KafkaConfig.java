package org.licesys.license.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String uri;

    private ProducerFactory<String, String> producerFactory() {

        Map<String, Object> settings = new HashMap<>();
        settings.put(BOOTSTRAP_SERVERS_CONFIG, uri);
        settings.put(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        settings.put(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(settings);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaAdmin admin(){
        Map<String, Object> configs = new HashMap<>();
        configs.put(BOOTSTRAP_SERVERS_CONFIG, uri);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic auditTopic() {
        return createTopicIfNotExists("audit-topic", 1, 1);
    }

    @Bean
    public NewTopic licenseTopic() {
        return createTopicIfNotExists("license-topic", 3, 1);
    }

    @Bean
    public NewTopic ownerTopic() {
        return createTopicIfNotExists("owner-topic", 2, 1);
    }

    @Bean
    public AdminClient adminClient() {
        return AdminClient.create(admin().getConfigurationProperties());
    }

    private NewTopic createTopicIfNotExists(String topicName, int partitions, int replicationFactor) {
        try {
            KafkaFuture<Boolean> future = adminClient().listTopics().names().thenApply(names -> names.contains(topicName));
            if (!future.get()) {
                return TopicBuilder.name(topicName)
                        .partitions(partitions)
                        .replicas(replicationFactor)
                        .build();
            }else{
                System.out.println("Topic " + topicName + " already exists");
            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("[There's an error in KafkaConfig class]");
            e.printStackTrace();
        }
        return null;
    }
}
