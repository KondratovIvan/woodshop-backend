package devops.ecom.customerservice.repos;

import devops.ecom.customerservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findOrdersByCustomerId(String customerId);
}
