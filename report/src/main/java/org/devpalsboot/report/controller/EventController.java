package org.devpalsboot.report.controller;

import lombok.RequiredArgsConstructor;
import org.devpalsboot.report.service.ConsumerService;
import org.devpalsboot.report.service.ProducerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final ProducerService producerService;
    private final ConsumerService consumerService;

    /**
     * 보고서 생성 완료 이벤트 produce
     * @param message
     * @return
     */
    @PostMapping("/report/completion")
    public String sendReportCompletionEvent(@RequestParam("message") String message) {
        producerService.sendReportCompletionEvent(message);
        return "Message sent to Kafka: " + message;
    }

    /**
     * 보고서 생성 요청 이벤트 consume
     * @return
     */
    @GetMapping("/report/creation")
    public List<String> consumeMessages() {
        return consumerService.getMessages();
    }

}
