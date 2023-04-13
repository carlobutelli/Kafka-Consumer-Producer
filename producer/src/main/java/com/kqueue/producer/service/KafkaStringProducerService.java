package com.kqueue.producer.service;

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
public class KafkaStringProducerService {

    Logger log = LoggerFactory.getLogger(KafkaStringProducerService.class);

    @Value("${kafka.topics.string-data}")
    private String topic;

    @Autowired
    @Qualifier("kafkaTemplateStringProducerFactory")
    private KafkaTemplate<String, String> kafkaTemplate;

    public String send(@RequestBody String message) {
        log.info("< Producing msg: {}>", message);
        kafkaTemplate.send(topic, message);
        return String.format("Message published correctly to <Topic: %s>", topic);
    }


}
