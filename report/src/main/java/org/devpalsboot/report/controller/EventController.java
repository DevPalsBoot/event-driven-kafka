package org.devpalsboot.report.controller;

import lombok.RequiredArgsConstructor;
import org.devpalsboot.report.domain.ReportComplete;
import org.devpalsboot.report.service.ConsumerService;
import org.devpalsboot.report.service.ProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
     * @return
     */
    @PostMapping("/report/completion")
    public ResponseEntity<ReportComplete> sendReportCompletionEvent(@RequestBody ReportComplete reportComplete) {
        producerService.sendReportCompletionEvent(reportComplete);
        return new ResponseEntity<ReportComplete>(reportComplete, HttpStatus.OK);
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
