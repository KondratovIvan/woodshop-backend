package devops.ecom.customerservice.repos;

import devops.ecom.customerservice.model.DeliveryData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryDataRepository extends MongoRepository<DeliveryData, String> {
}
