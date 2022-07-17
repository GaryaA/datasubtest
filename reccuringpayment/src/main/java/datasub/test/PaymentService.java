package datasub.test;

import datasub.test.conf.Conf;
import datasub.test.domain.Check;
import datasub.test.domain.Payment;
import datasub.test.repo.CheckRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.format.DateTimeFormatter;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    @Value("${payments-service-url}")
    private String paymentsServiceUrl;

    @Autowired
    private CheckRepo checkRepo;

    @Autowired
    private RestTemplate restClient;

    public void sendPayment(Payment payment) {
        log.debug(String.format("Payment with number %d amount %s bank_account %s is sent.",
                payment.getNumber(),
                payment.getAmount().toPlainString(),
                payment.getBankAccount()
        ));
        ResponseEntity<Check> response = restClient.postForEntity(paymentsServiceUrl, payment, Check.class);
        Check check = response.getBody();
        if (response.getStatusCodeValue() != 200 || check == null) {
            log.error("Payment service exception: " + response.toString());
            throw new RuntimeException("Payment service exception: " + response.toString());
        }
        log.debug(String.format("Received check number %d, amount %s, date_time %s ",
                check.getNumber(),
                check.getAmount().toPlainString(),
                check.getDateTime().format(DateTimeFormatter.ofPattern(Conf.DATE_TIME_FORMAT))
        ));
    }

}


