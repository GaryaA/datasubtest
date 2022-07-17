package datasub.test;

import datasub.test.domain.RecurringCycle;
import datasub.test.domain.RecurringPayment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestRecurringPayments {

    private static final Logger log = LoggerFactory.getLogger(TestRecurringPayments.class);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testRecurringCycleDay() {
        RecurringPayment recurringPayment = new RecurringPayment();
        recurringPayment.setNumber(24);
        recurringPayment.setAmount(new BigDecimal(1000));
        recurringPayment.setBankAccount("bankkkk");
        recurringPayment.setRecurringStartDate(LocalDate.of(2022, 7, 16));
        recurringPayment.setRecurringCycle(RecurringCycle.DAY);

        ResponseEntity<String> recPaymentResponse = restTemplate.postForEntity("http://localhost:" + port + "/recurring-payments", recurringPayment, String.class);
        Assertions.assertEquals(recPaymentResponse.getStatusCodeValue(), 200);
        Assertions.assertNotNull(recPaymentResponse.getBody());
        String recPaymentId = recPaymentResponse.getBody();
        recPaymentId = recPaymentId.substring(1, recPaymentId.length() - 1);

        ResponseEntity<String> scheduleResponse = restTemplate.getForEntity("http://localhost:" + port + "/recurring-payments/" + recPaymentId + "/schedule", String.class);
        Assertions.assertEquals(scheduleResponse.getStatusCodeValue(), 200);
        Assertions.assertNotNull(scheduleResponse.getBody());

        log.info("Schedule for recurring payment: " + scheduleResponse.getBody());

        String scheduleStr = scheduleResponse.getBody();
        assertThat(scheduleStr)
                .containsIgnoringCase("Recurring payment id " + recPaymentId)
                .containsIgnoringCase("trigger interval type hour")
                .containsIgnoringCase("interval step 24")
                .containsIgnoringCase("start at 07/16/2022");
    }

}
