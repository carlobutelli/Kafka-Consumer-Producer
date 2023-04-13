package com.kqueue.producer.config;

import com.kqueue.producer.model.KafkaMessage;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.DelegatingByTypeSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class KafkaJsonProducerConfig {

    @Value("${kafka.server.address}")
    private String kafkaAddress;
    @Value("${kafka.topics.json-data}")
    private String topic;
    @Value("${kafka.client-id}")
    private String clientId;

    @Bean
    public ProducerFactory jsonProducerFactory() {
        var randomKafkaId = UUID.randomUUID();
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.CLIENT_ID_CONFIG, clientId + randomKafkaId);
        return new DefaultKafkaProducerFactory(
                config,
                new StringSerializer(),
                new DelegatingByTypeSerializer(
                        Map.of(byte[].class, new ByteArraySerializer(), KafkaMessage.class, new JsonSerializer<>())
                )
        );
    }

    @Bean(name = "kafkaTemplateJsonProducerFactory")
    public KafkaTemplate<String, KafkaMessage> kafkaTemplateJsonProducerFactory() {
        return new KafkaTemplate<>(jsonProducerFactory());
    }

    @Bean
    public NewTopic topicJson() {
        return new NewTopic(topic, 2, (short) 1);
    }
}
