package org.devpalsboot.backend;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.awaitility.Awaitility;
import org.devpalsboot.backend.consumer.service.ConsumerService;
import org.devpalsboot.backend.producer.service.ProducerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@EmbeddedKafka(topics = {ProducerService.REPORT_CREATION, ConsumerService.REPORT_COMPLETE})
public class KafkaIntegrationTest {

    @Autowired
    private ProducerService producerService;
    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final LinkedBlockingQueue<String> messages = new LinkedBlockingQueue<>();

    @KafkaListener(topics = ProducerService.REPORT_CREATION, groupId = "test-group")
    public void listen(ConsumerRecord<String, String> record) {
        messages.add(record.value());
    }

    @BeforeEach
    public void setup() {
        messages.clear();
    }

    @Test
    @DisplayName("백엔드 모듈 producer test")
    public void backendProducerTest() throws Exception {
        // given
        String msg = "[REPORT_CREATION] testing producerService";

        // when
        producerService.sendCreateReport(msg);

        // then
        String receivedMessage = messages.poll(10, TimeUnit.SECONDS);
        System.out.println(receivedMessage);
        assertThat(receivedMessage).isEqualTo(msg);
    }

    @Test
    @DisplayName("백엔드 모듈 consumer test")
    public void backendConsumerTest() throws InterruptedException {
        //given
        String msg = "[REPORT_COMPLETE] testing consumerService";
        kafkaTemplate.send(ConsumerService.REPORT_COMPLETE, msg);

        Thread.sleep(1000);

        // when & then
        Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> {

                    List<Object> receivedMessages = consumerService.getMessages();

                    Object receivedMessage = receivedMessages.get(0);
                    if (receivedMessage instanceof ConsumerRecord) {
                        String actualMessage = ((ConsumerRecord<String, String>) receivedMessage).value();
                        assertThat(actualMessage).isEqualTo(msg);
                    }
                });

    }
}
