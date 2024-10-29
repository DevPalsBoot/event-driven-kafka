package org.devpalsboot.report.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devpalsboot.report.domain.ReportComplete;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProducerService {

    public static final String REPORT_COMPLETE = "REPORT_COMPLETE";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendReportCompletionEvent(ReportComplete reportComplete) {
        log.info("Report completion event sent: " + reportComplete);
        kafkaTemplate.send(REPORT_COMPLETE, reportComplete);
    }
}
