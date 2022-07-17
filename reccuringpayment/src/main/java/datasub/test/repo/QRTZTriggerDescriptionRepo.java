package datasub.test.repo;

import datasub.test.domain.QRTZTriggerDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QRTZTriggerDescriptionRepo extends JpaRepository<QRTZTriggerDescription, UUID> {

    QRTZTriggerDescription findByJobKey(String jobKey);

}
