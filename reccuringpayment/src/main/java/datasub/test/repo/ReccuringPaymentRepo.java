package datasub.test.repo;

import datasub.test.domain.RecurringPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReccuringPaymentRepo extends JpaRepository<RecurringPayment, UUID> {


}
