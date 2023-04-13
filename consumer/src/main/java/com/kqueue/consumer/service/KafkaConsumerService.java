package com.kqueue.consumer.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kqueue.consumer.exception.CustomException;
import com.kqueue.consumer.model.KafkaMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumerService implements ConsumerMessage {

    Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Override
    @KafkaListener(
            topics = "${kafka.topics.string-data}",
            groupId = "${kafka.groups.string}",
            clientIdPrefix = "OppString",
            containerFactory = "stringDataContainerFactory"
    )
    public void receiveStringData(String message, ConsumerRecord<String, Object> cr) throws IOException {
        try {
            log.debug("START method receiveStringData from topic: {}", cr.topic());
            var objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            log.info(String.format("consumed (string) message <%s>", message));
            log.debug("END method receiveStringData()");
        } catch (Exception e) {
            log.error("ERROR method receiveMessage: {}", e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    @KafkaListener(
            topics = "${kafka.topics.json-data}",
            groupId = "${kafka.groups.json}",
            clientIdPrefix = "OppJson",
            containerFactory = "jsonKafkaMessageContainerFactory"
    )
    public void receiveJsonData(KafkaMessage kafkaMessage, ConsumerRecord<String, Object> cr) {
        try {
            log.debug("START method receiveJsonData from topic: {}", cr.topic());
            var objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            log.info(String.format("consumed (json) message <%s>", kafkaMessage));
            log.debug("END method receiveJsonData()");
        } catch (Exception e) {
            log.error("ERROR method receiveMessage");
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    //    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.group.id}")
    //    public void listener(ConsumerRecord<String, Object> cr) {
    //        log.info(cr.toString());
    //        log.info("< Consumed Data: {} >", cr.value());
    //    }
}
