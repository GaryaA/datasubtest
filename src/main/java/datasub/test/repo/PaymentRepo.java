package datasub.test.repo;

import datasub.test.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Integer> {

    Payment getByNumber(Integer number);
}
