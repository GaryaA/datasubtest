package datasub.test.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "recurring_payments")
public class RecurringPayment {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer number;
    @Column
    private BigDecimal amount;
    @Column
    private String memo;
    @Column
    private String email;
    @Column
    private String name;
    @Column
    private String bankAccount;
    @Column
    private boolean recurring;
    @Column
    private RecurringCycle recurringCycle;
    @Column
    private LocalDate recurringStartDate;
    @Column
    private Integer recurringInstallments;
    @Column
    private String jobKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public RecurringCycle getRecurringCycle() {
        return recurringCycle;
    }

    public void setRecurringCycle(RecurringCycle recurringCycle) {
        this.recurringCycle = recurringCycle;
    }

    public LocalDate getRecurringStartDate() {
        return recurringStartDate;
    }

    public void setRecurringStartDate(LocalDate recurringStartDate) {
        this.recurringStartDate = recurringStartDate;
    }

    public Integer getRecurringInstallments() {
        return recurringInstallments;
    }

    public void setRecurringInstallments(Integer recurringInstallments) {
        this.recurringInstallments = recurringInstallments;
    }

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }
}
