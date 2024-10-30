package org.devpalsboot.backend.consumer;

import lombok.RequiredArgsConstructor;
import org.devpalsboot.backend.consumer.service.ConsumerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event/back")
@RequiredArgsConstructor
public class EventConsumer {

    private final ConsumerService consumerService;

    /**
     * 보고서 생성 완료
     */
    @GetMapping("/report/completion")
    public List<Object> consumeReportComplete(){
        return consumerService.getMessages();
    }
}
