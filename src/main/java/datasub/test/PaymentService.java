package datasub.test;

import datasub.test.entity.Check;
import datasub.test.entity.Payment;
import datasub.test.repo.CheckRepo;
import datasub.test.repo.PaymentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    Logger log = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private CheckRepo checkRepo;

    public Check createPayment(Payment payment) {
        log.debug(String.format("Received payment with number %d amount %s bank_account %s",
                payment.getNumber(),
                payment.getAmount().toPlainString(),
                payment.getBankAccount()
        ));
        Payment createdPayment = paymentRepo.save(payment);
        return checkRepo.save(convertToCheck(createdPayment));
    }

    public Payment getPayment(Integer id) {
        return paymentRepo.getById(id);
    }

    public Payment getPaymentByNumber(Integer number) {
        return paymentRepo.getByNumber(number);
    }

    private Check convertToCheck(Payment reccuringPayment) {
        Check check = new Check();
        check.setAmount(reccuringPayment.getAmount());
        check.setBankAccount(reccuringPayment.getBankAccount());
        check.setEmail(reccuringPayment.getEmail());
        check.setMemo(reccuringPayment.getMemo());
        check.setNumber(reccuringPayment.getNumber());
        return check;
    }

}
