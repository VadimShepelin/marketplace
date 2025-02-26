package com.spring.marketplace.kafka;

import com.spring.marketplace.events.EventSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventProducer {

    @Value("${app.kafka.topic.name}")
    private String topicName;
    private final KafkaTemplate<String, EventSource> kafkaTemplate;

    public void sendCreateOrderEvent(EventSource eventSource, UUID userId, UUID idempotencyKey) {
        log.info("calling method sendCreateOrderEvent with event: {}", eventSource);

        ProducerRecord<String, EventSource> producerRecord = new ProducerRecord<String, EventSource>(
                topicName, UUID.randomUUID().toString(), eventSource
        );
        producerRecord.headers().
                add("user_id", userId.toString().getBytes()).
                add("idempotency_key", idempotencyKey.toString().getBytes());

        try {
            kafkaTemplate.send(producerRecord);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public void sendUpdateOrderStatusEvent(EventSource eventSource, UUID orderId) {
        log.info("calling method sendUpdateOrderStatusEvent with event: {}", eventSource);

        ProducerRecord<String, EventSource> producerRecord = new ProducerRecord<String, EventSource>(
                topicName, UUID.randomUUID().toString(), eventSource
        );
        producerRecord.headers().
                add("order_id", orderId.toString().getBytes());

        try {
            kafkaTemplate.send(producerRecord);
        }
        catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

}
