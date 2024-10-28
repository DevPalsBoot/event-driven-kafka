package org.devpalsboot.report.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerService {

    public static final String REPORT_COMPLETE = "REPORT_COMPLETE";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendReportCompletionEvent(String message) {
        kafkaTemplate.send(REPORT_COMPLETE, message);
        log.info("Report completion event sent: " + message);
    }
}
