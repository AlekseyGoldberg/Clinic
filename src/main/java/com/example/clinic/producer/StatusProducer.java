package com.example.clinic.producer;

import com.example.clinic.dto.StatusProducerDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class StatusProducer {
    @Value("${app.kafka.producer.topic}")
    private String topic;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendStatus(StatusProducerDto dto, String logMessage) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(dto);
        log.info(logMessage, message);
        kafkaTemplate.send(topic, message);
    }
}
