package datasub.test;

import datasub.test.domain.RecurringPayment;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("recurring-payments")
public class ReccuringPaymentResource {
    private static final Logger log = LoggerFactory.getLogger(ReccuringPaymentResource.class);

    @Autowired
    private RecurringPaymentService service;


    @PostMapping
    public UUID schedulePayment(@RequestBody RecurringPayment recurringPayment) throws SchedulerException {
        return service.schedulePayment(recurringPayment);
    }

    @GetMapping("/{id}/schedule")
    public String getPaymentSchedule(@PathVariable UUID id) {
        return service.getPaymentSchedule(id);
    }


}
