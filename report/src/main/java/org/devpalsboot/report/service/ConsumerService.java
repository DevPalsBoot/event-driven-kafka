package org.devpalsboot.report.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ConsumerService {
    public static final String REPORT_CREATION= "REPORT_CREATION";
    private final List<String> messages = new ArrayList<>();

    @KafkaListener(topics = REPORT_CREATION, groupId = "example-group")
    public void listen(String message) {
        messages.add(message);
        log.info("Received message: " + message);
    }

    public List<String> getMessages() {
        return messages;
    }
}
