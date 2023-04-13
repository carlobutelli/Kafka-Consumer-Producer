package com.kqueue.producer.service;

import com.kqueue.producer.model.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class KafkaJsonProducerService {

    Logger log = LoggerFactory.getLogger(KafkaStringProducerService.class);

    @Value("${kafka.topics.json-data}")
    private String topic;

    @Autowired
    @Qualifier("kafkaTemplateJsonProducerFactory")
    private KafkaTemplate<String, KafkaMessage> jsonKafkaTemplate;

    public String sendJsonMessage(@RequestBody KafkaMessage kafkaMessage) {
        log.info("< Producing msg: {}>", kafkaMessage);
        jsonKafkaTemplate.send(topic, kafkaMessage);
        return String.format("Message published correctly to <Topic: %s>", topic);
    }
}
