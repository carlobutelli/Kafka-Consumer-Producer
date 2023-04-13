package com.kqueue.consumer.service;

import com.kqueue.consumer.model.KafkaMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.io.IOException;

public interface ConsumerMessage {
    void receiveStringData(String message, ConsumerRecord<String, Object> cr) throws IOException;
    void receiveJsonData(KafkaMessage kafkaMessage, ConsumerRecord<String, Object> cr);
}
