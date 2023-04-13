package com.kqueue.consumer.config;

import com.kqueue.consumer.model.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@EnableKafka
@Configuration
public class KafkaJsonMessageConfig {

    @Value("kafka.group.id")
    private String groupId;
    @Value("${kafka.server.address}")
    private String kafkaAddress;
    @Value("${kafka.client-id}")
    private String clientId;

    @Bean
    ConsumerFactory<String, KafkaMessage> jsonKafkaMessageFactory() {
        var randomKafkaId = UUID.randomUUID();
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class.getName());
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, KafkaMessage.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.kqueue.consumer");
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId + randomKafkaId);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(name = "jsonKafkaMessageContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> jsonKafkaMessageContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(jsonKafkaMessageFactory());
//        factory.setContainerCustomizer(container -> container.getContainerProperties().setAuthExceptionRetryInterval(Duration.ofSeconds(5L)));
        return factory;
    }
}
