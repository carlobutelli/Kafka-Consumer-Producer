package com.kqueue.consumer.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@EnableKafka
@Configuration
public class KafkaStringDataConfig {

    @Value("kafka.group.id")
    private String groupId;
    @Value("${kafka.server.address}")
    private String kafkaAddress;
    @Value("${kafka.client-id}")
    private String clientId;

    @Bean
    ConsumerFactory<String, String> stringDataConsumerFactory() {
        var randomKafkaId = UUID.randomUUID();
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId + randomKafkaId);
        props.put("allow.auto.create.topics", "true");
        log.debug("consumerFactory configProps: {}", props);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(name = "stringDataContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> stringDataContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringDataConsumerFactory());
//        factory.setContainerCustomizer(container -> container.getContainerProperties().setAuthExceptionRetryInterval(Duration.ofSeconds(5L)));
        return factory;
    }
}
