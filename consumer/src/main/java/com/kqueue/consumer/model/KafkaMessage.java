package com.kqueue.consumer.model;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KafkaMessage {
    private String id = UUID.randomUUID().toString();
    private String message;
    private String owner;
    private LocalDate messageDate = LocalDate.now();
}