package datasub.test.repo;

import datasub.test.entity.Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckRepo extends JpaRepository<Check, Integer> {
}
