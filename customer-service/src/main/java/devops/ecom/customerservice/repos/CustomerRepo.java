package devops.ecom.customerservice.repos;

import devops.ecom.customerservice.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CustomerRepo extends MongoRepository<Customer, String> {

}
