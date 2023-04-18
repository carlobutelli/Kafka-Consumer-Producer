package com.kqueue.producer.controller;

import com.kqueue.producer.model.KafkaMessage;
import com.kqueue.producer.service.KafkaJsonProducerService;
import com.kqueue.producer.service.KafkaStringProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
public class ProduceMessageController {

    private final KafkaStringProducerService kafkaStringProducerService;

    private final KafkaJsonProducerService kafkaJsonProducerService;

    @RequestMapping("/")
    public String home() {
        return "Hello from producer";
    }

    @PostMapping("/send-json")
    public String sendAccountData(@RequestBody KafkaMessage kafkaMessage) {
        return kafkaJsonProducerService.sendJsonMessage(kafkaMessage);
    }

    @PostMapping("/send-string")
    public String publishMessage(@RequestBody KafkaMessage kafkaMessage) {
        return kafkaStringProducerService.send(kafkaMessage.toString());
    }
}
