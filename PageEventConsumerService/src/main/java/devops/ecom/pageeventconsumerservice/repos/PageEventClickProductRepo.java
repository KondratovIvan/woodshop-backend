package devops.ecom.pageeventconsumerservice.repos;

import devops.ecom.pageeventconsumerservice.entities.PageEventClickProduct;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PageEventClickProductRepo extends MongoRepository<PageEventClickProduct, String> {
}
