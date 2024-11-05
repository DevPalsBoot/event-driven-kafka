package org.devpalsboot.report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.devpalsboot.report.domain.ReportComplete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * 보고서 통합 테스트
 */
@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"TEST_REPORT_CREATION", "TEST_REPORT_COMPLETE"})
public class KafkaIntegrationTest {
    public static final String CREATE_REQUEST_EVENT_VALUE = "report create test value";
    public static final String EXAMPLE_GROUP = "test-example-group";
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    private ConsumerFactory<String, Object> consumerFactory;
    private CountDownLatch latch;
    private String receivedCompleteEvent;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        latch = new CountDownLatch(1);
        receivedCompleteEvent = null;
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testReportKafkaMainLogic() throws InterruptedException, JsonProcessingException {
        // given
        kafkaTemplate.send("TEST_REPORT_CREATION", CREATE_REQUEST_EVENT_VALUE);
        boolean messageConsumed = latch.await(30, TimeUnit.SECONDS);
        assertThat(messageConsumed).isTrue();
        assertThat(receivedCompleteEvent).isEqualTo(CREATE_REQUEST_EVENT_VALUE);

        // when
        System.out.println("보고서 생성 시작!");
        Thread.sleep(3000);
        ReportComplete reportComplete = new ReportComplete("test/report.pdf", ReportStatus.COMPLETE);
        System.out.println("보고서 생성 완료!");
        kafkaTemplate.send("TEST_REPORT_COMPLETE", reportComplete);

        // then
        try (Consumer<String, Object> consumer = consumerFactory.createConsumer()) {
            embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, "TEST_REPORT_COMPLETE");
            ConsumerRecord<String, Object> completeEventRecord = KafkaTestUtils.getSingleRecord(consumer, "TEST_REPORT_COMPLETE");
            assertThat(completeEventRecord.value()).isEqualTo(objectMapper.writeValueAsString(reportComplete));
        }
    }

    @KafkaListener(topics = "TEST_REPORT_CREATION", groupId = EXAMPLE_GROUP)
    public void consumeCreateRequest(ConsumerRecord<String, Object> record) {
        String value = (String) record.value();
        receivedCompleteEvent = value.replace("\"", "");
        latch.countDown(); // 요청 이벤트 수신 완료 표시
    }

}
