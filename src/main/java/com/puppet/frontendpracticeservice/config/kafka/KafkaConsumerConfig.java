package com.puppet.frontendpracticeservice.config.kafka;

import com.puppet.frontendpracticeservice.domain.kafka.ImageKafka;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

/**
 * Настройка сonsumer Kafka, создает две конфигурации для @KafkaListener - String и Json
 */
@Component
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrap;

    @Value("${spring.kafka.template.default-topic}")
    private String defaultTopic;

    @Value("${spring.kafka.consumers.consumer-one.group-id}")
    private String stringGroup;
    @Value("${spring.kafka.consumers.consumer-two.group-id}")
    private String jsonGroup;

    /**
     * Создает ConsumerFactory для String-десериализатора.
     *
     * @return ConsumerFactory для обработки строковых сообщений.
     */
    @Bean
    public ConsumerFactory<String, String> consumerString() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, stringGroup);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * Создает KafkaListenerContainer для обработки строковых сообщений.
     * Устанавливает фабрику потребителей и настраивает параллельную обработку сообщений
     * с помощью параметра concurrency.
     *
     * @param consumerString ConsumerFactory для обработки строковых сообщений
     * @return KafkaListenerContainer для обработки строковых сообщений.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerString(
            ConsumerFactory<String, String> consumerString) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerString);
        factory.setConcurrency(3);
        return factory;
    }

    /**
     * Создает ConsumerFactory для Json-десериализатора с возможностью выбросить исключение.
     *
     * @return ConsumerFactory для обработки сообщений в формате Json.
     */
    @Bean
    public ConsumerFactory<String, ImageKafka> consumerJson() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, jsonGroup);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.puppet.frontendpracticeservice.domain.kafka");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * Создает KafkaListenerContainer для обработки сообщений в формате Json.
     * Устанавливает фабрику потребителей и параллельную обработку сообщений
     * с помощью параметра concurrency. Так же настраивает обработку ошибок с
     * помощью DLQ и стратегию повторных попыток через DefaultErrorHandler
     *
     * @param consumerJson ConsumerFactory для обработки сообщений в формате Json
     * @param kafkaTemplate для отправки сообщений в Kafka, используемый для конфигурации DLQ.
     * @return KafkaListenerContainer для обработки сообщений в формате Json.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ImageKafka> kafkaListenerContainerJson(
            ConsumerFactory<String, ImageKafka> consumerJson,
            KafkaTemplate<String, ImageKafka> kafkaTemplate) {

        DeadLetterPublishingRecoverer letter = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (ConsumerRecord<?, ?> record, Exception ex) ->
                        new TopicPartition(defaultTopic, 0));

        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler(letter, new FixedBackOff(1000L, 3));

        ConcurrentKafkaListenerContainerFactory<String, ImageKafka> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerJson);
        factory.setCommonErrorHandler(defaultErrorHandler);
        factory.setConcurrency(2);
        return factory;
    }
}