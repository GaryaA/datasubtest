package datasub.test;

import datasub.test.domain.Payment;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PaymentJob extends QuartzJobBean {

    private static final Logger log = LoggerFactory.getLogger(ReccuringPaymentResource.class);

    @Autowired
    private PaymentService paymentService;


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        paymentService.sendPayment(toPayment(jobDataMap));
    }

    private Payment toPayment(JobDataMap dataMap) {
        Payment payment = new Payment();
        payment.setNumber(dataMap.getInt("number"));
        payment.setBankAccount(dataMap.getString("bank_account"));
        payment.setAmount((BigDecimal) dataMap.get("amount"));
        payment.setEmail(dataMap.getString("email"));
        payment.setMemo(dataMap.getString("memo"));
        payment.setName(dataMap.getString("name"));
        return payment;
    }
}
