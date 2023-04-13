package com.kqueue.producer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KafkaMessage {
    private String id = UUID.randomUUID().toString();
    private String message;
    private String owner;
    private LocalDate messageDate = LocalDate.now();
}