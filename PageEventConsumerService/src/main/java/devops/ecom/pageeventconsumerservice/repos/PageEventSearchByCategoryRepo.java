package devops.ecom.pageeventconsumerservice.repos;

import devops.ecom.pageeventconsumerservice.entities.PageEventSearchByCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PageEventSearchByCategoryRepo extends MongoRepository<PageEventSearchByCategory, String> {
}
