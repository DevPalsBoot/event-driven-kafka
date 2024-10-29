package org.devpalsboot.backend.consumer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumerService {

    public static final String REPORT_COMPLETE = "REPORT_COMPLETE";
    private List<String> messages = new ArrayList<>();

    @KafkaListener(topics = REPORT_COMPLETE, groupId = "example-group")
    public void reportCompleteListener(String message) {
        messages.add(message);
    }

    public List<String> getMessages()   {
        return this.messages;
    }
}
