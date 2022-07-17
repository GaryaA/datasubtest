package datasub.test;

import datasub.test.conf.Conf;
import datasub.test.domain.QRTZTriggerDescription;
import datasub.test.domain.RecurringCycle;
import datasub.test.domain.RecurringPayment;
import datasub.test.repo.QRTZTriggerDescriptionRepo;
import datasub.test.repo.ReccuringPaymentRepo;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecurringPaymentService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ReccuringPaymentRepo repo;

    @Autowired
    private QRTZTriggerDescriptionRepo triggersRepo;

    @Transactional
    public UUID schedulePayment(RecurringPayment recurringPayment) throws SchedulerException {
        JobDetail jobDetail = buildJobDetail(recurringPayment);
        Trigger trigger = buildJobTrigger(jobDetail, recurringPayment.getRecurringCycle(), recurringPayment.getRecurringStartDate());
        scheduler.scheduleJob(jobDetail, trigger);
        recurringPayment.setJobKey(jobDetail.getKey().getName());
        return repo.save(recurringPayment).getId();
    }

    public String getPaymentSchedule(UUID id) {
        Optional<RecurringPayment> recPaymentOpt = repo.findById(id);
        if (recPaymentOpt.isEmpty()) {
            return null;
        }
        RecurringPayment recurringPayment = recPaymentOpt.get();
        QRTZTriggerDescription triggerDescription = triggersRepo.findByJobKey(recurringPayment.getJobKey());

        return String.format(
                "Recurring payment id %s, trigger interval type %s, interval step %d, start at %s, prev fire time %s, next fire time %s",
                recurringPayment.getId(),
                triggerDescription.getIntervalType(),
                triggerDescription.getIntervalStep(),
                triggerDescription.getStartTime().format(DateTimeFormatter.ofPattern(Conf.DATE_TIME_FORMAT)),
                triggerDescription.getPrevFireTime().format(DateTimeFormatter.ofPattern(Conf.DATE_TIME_FORMAT)),
                triggerDescription.getNextFireTime().format(DateTimeFormatter.ofPattern(Conf.DATE_TIME_FORMAT))
        );
    }

    private JobDetail buildJobDetail(RecurringPayment recurringPayment) {
        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("number", recurringPayment.getNumber());
        jobDataMap.put("amount", recurringPayment.getAmount());
        jobDataMap.put("bank_account", recurringPayment.getBankAccount());
        jobDataMap.put("email", recurringPayment.getEmail());
        jobDataMap.put("memo", recurringPayment.getMemo());
        jobDataMap.put("name", recurringPayment.getName());

        return JobBuilder.newJob(PaymentJob.class)
                .withIdentity(UUID.randomUUID().toString(), "payment-jobs")
                .withDescription("Send Payment")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, RecurringCycle recurringCycle, LocalDate startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "payment-triggers")
                .withDescription("Send Payment Trigger")
                .startAt(Date.from(startAt.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withSchedule(buildSchedule(recurringCycle))
                .build();
    }

    private ScheduleBuilder buildSchedule(RecurringCycle recurringCycle) {
        if (recurringCycle == null) {
            throw new IllegalArgumentException("recurringCycle must not be null");
        }
        //Отправление платежей в 7:00. Если шедулер был отключен в нужное по графику время, то пропущенный платеж отправится при следующем срабатывании.
        DailyTimeIntervalScheduleBuilder result = DailyTimeIntervalScheduleBuilder
                .dailyTimeIntervalSchedule()
                .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(7, 0))
                .withMisfireHandlingInstructionFireAndProceed();
        switch (recurringCycle) {
            case DAY:
                result = result.onMondayThroughFriday().withIntervalInHours(24);
                break;
            case WEEK:
                result = result.withInterval(1, DateBuilder.IntervalUnit.WEEK);
                break;
            case BI_WEEKLY:
                result = result.withInterval(2, DateBuilder.IntervalUnit.WEEK);
                break;
            case MONTH:
                result = result.withInterval(1, DateBuilder.IntervalUnit.MONTH);
                break;
        }
        return result;
    }

}
