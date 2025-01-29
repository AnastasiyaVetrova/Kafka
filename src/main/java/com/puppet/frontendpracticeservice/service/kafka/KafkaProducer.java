package com.puppet.frontendpracticeservice.service.kafka;

import com.puppet.frontendpracticeservice.domain.kafka.ImageKafka;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Сервис для отправки сообщений.
 */
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> stringKafkaTemplate;
    private final KafkaTemplate<String, ImageKafka> jsonKafkaTemplate;

    @Value("${spring.kafka.template.default-topic}")
    private String defaultTopic;


    public void sendMessage(String message) {
        CompletableFuture<SendResult<String, String>> future = stringKafkaTemplate.send(defaultTopic, message);
        future.thenAccept(sendResult -> System.out.println("Отправлено с ключом " + sendResult.getProducerRecord().key() +
                " в партицию: " + sendResult.getRecordMetadata().partition() +
                " в топик: " + sendResult.getRecordMetadata().topic() +
                " с оффсетом: " + sendResult.getRecordMetadata().offset())).exceptionally(ex -> {
            System.err.println("Ошибка: " + ex.getMessage());
            return null;
        });
    }

    @Async
    public void sendMessage(EnumKafkaTopic topic, ImageKafka message) {
        jsonKafkaTemplate.send(topic.getTopic(), message);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName());
    }
}
