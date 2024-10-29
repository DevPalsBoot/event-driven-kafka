package org.devpalsboot.backend.producer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerService {

    public static final String REPORT_CREATION= "REPORT_CREATION";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendCreateReport(String message) {
        kafkaTemplate.send(REPORT_CREATION, message);
    }
}
