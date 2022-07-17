package datasub.test.domain;

import datasub.test.conf.LocalDateTimeLongConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "QRTZ_SIMPROP_TRIGGERS")
@SecondaryTable(name = "QRTZ_TRIGGERS")
public class QRTZTriggerDescription {

    @Id
    @Column(name = "TRIGGER_NAME")
    private String key;

    @Column(name = "STR_PROP_1")
    private String intervalType;

    @Column(name = "INT_PROP_1")
    private int intervalStep;

    @Column(name = "JOB_NAME", table = "QRTZ_TRIGGERS")
    private String jobKey;

    @Column(name = "START_TIME", table = "QRTZ_TRIGGERS")
    @Convert(converter = LocalDateTimeLongConverter.class)
    private LocalDateTime startTime;

    @Column(name = "PREV_FIRE_TIME", table = "QRTZ_TRIGGERS")
    @Convert(converter = LocalDateTimeLongConverter.class)
    private LocalDateTime prevFireTime;

    @Column(name = "NEXT_FIRE_TIME", table = "QRTZ_TRIGGERS")
    @Convert(converter = LocalDateTimeLongConverter.class)
    private LocalDateTime nextFireTime;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }

    public String getIntervalType() {
        return intervalType;
    }

    public void setIntervalType(String intervalType) {
        this.intervalType = intervalType;
    }

    public int getIntervalStep() {
        return intervalStep;
    }

    public void setIntervalStep(int intervalStep) {
        this.intervalStep = intervalStep;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getPrevFireTime() {
        return prevFireTime;
    }

    public void setPrevFireTime(LocalDateTime prevFireTime) {
        this.prevFireTime = prevFireTime;
    }

    public LocalDateTime getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(LocalDateTime nextFireTime) {
        this.nextFireTime = nextFireTime;
    }
}
