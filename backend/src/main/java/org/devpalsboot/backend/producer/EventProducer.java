package org.devpalsboot.backend.producer;

import lombok.RequiredArgsConstructor;
import org.devpalsboot.backend.producer.service.ProducerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event/back")
@RequiredArgsConstructor
public class EventProducer {

    private final ProducerService producerService;

    @GetMapping("/report/creation")
    public void produceReportCreate(@RequestParam("msg") String msg) {
        producerService.sendCreateReport(msg);
    }
}
