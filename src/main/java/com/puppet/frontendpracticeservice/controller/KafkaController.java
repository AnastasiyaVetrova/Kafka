package com.puppet.frontendpracticeservice.controller;

import com.puppet.frontendpracticeservice.domain.kafka.NameKafka;
import com.puppet.frontendpracticeservice.service.kafka.EnumKafkaTopic;
import com.puppet.frontendpracticeservice.service.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Контроллер для экспериментов с Kafka
 */
@RestController
@RequestMapping("/frontend-practice/kafka")
@RequiredArgsConstructor
public class KafkaController {
    private final KafkaProducer kafkaProducer;

    @PostMapping("/str")
    public void sendTextMessage(@RequestParam String message) {
        kafkaProducer.sendMessage(message);
    }

    @PostMapping("/json")
    public void sendJsonMessage(@RequestBody NameKafka nameKafka) {
        kafkaProducer.sendMessage(EnumKafkaTopic.TOPIC_NAME_ONE, nameKafka);
    }

    @PostMapping("/whileW")
    public void sendTextMessageW() {
        int number = 0;

        while (number <= 1000) {
            number++;
            kafkaProducer.sendMessage(String.valueOf(number));
        }
    }

    @PostMapping("/whileQ")
    public void sendTextMessageQ() {
        int number = 1000;

        while (number <= 2000) {
            number++;
            kafkaProducer.sendMessage(String.valueOf(number));
        }
    }
}