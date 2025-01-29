package com.puppet.frontendpracticeservice.config.kafka;

import com.puppet.frontendpracticeservice.domain.kafka.ImageKafka;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Настройка producer Kafka. Создает два KafkaTemplate, отправляющих сообщения в форматах String и Json.
 */
@Component
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrap;

    /**
     * Создает ProducerFactory для String-сериализатора.
     *
     * @return ProducerFactory для отправки строковых сообщений.
     */
    @Bean
    public ProducerFactory<String, String> producerString() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, "50");
        return new DefaultKafkaProducerFactory<>(props);
    }

    /**
     * Создает KafkaTemplate для отправки строковых сообщений.
     *
     * @param producerString ProducerFactory для отправки строковых сообщений.
     * @return KafkaTemplate для отправки строковых сообщений.
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplateString(ProducerFactory<String, String> producerString) {
        return new KafkaTemplate<>(producerString);
    }

    /**
     * Создает ProducerFactory для Json-сериализатора.
     *
     * @return ProducerFactory для отправки сообщений в формате Json.
     */
    @Bean
    public ProducerFactory<String, ImageKafka> producerJson() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    /**
     * Создает KafkaTemplate для отправки сообщений в формате Json.
     *
     * @param producerString ProducerFactory для отправки сообщений в формате Json.
     * @return KafkaTemplate для отправки сообщений в формате Json.
     */
    @Bean
    public KafkaTemplate<String, ImageKafka> kafkaTemplateJson(ProducerFactory<String, ImageKafka> producerString) {
        return new KafkaTemplate<>(producerString);
    }
}