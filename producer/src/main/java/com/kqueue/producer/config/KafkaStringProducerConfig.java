package com.kqueue.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class KafkaStringProducerConfig {

    Logger log = LoggerFactory.getLogger(KafkaStringProducerConfig.class);

    @Value("${kafka.server.address}")
    private String kafkaAddress;
    @Value("${kafka.topics.string-data}")
    private String topic;
    @Value("${kafka.client-id}")
    private String clientId;

    @Bean
    public ProducerFactory<String, String> stringProducerFactory() {
        var randomKafkaId = UUID.randomUUID();
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, clientId + randomKafkaId);
        log.debug("producerFactory configProps: {}", props);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(name = "kafkaTemplateStringProducerFactory")
    public KafkaTemplate<String, String> kafkaTemplateStringProducerFactory() {
        return new KafkaTemplate<>(stringProducerFactory());
    }

    @Bean
    public NewTopic topicString() {
        return new NewTopic(topic, 2, (short) 1);
    }
}
