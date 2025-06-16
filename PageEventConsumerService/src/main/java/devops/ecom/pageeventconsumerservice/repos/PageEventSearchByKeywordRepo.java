package devops.ecom.pageeventconsumerservice.repos;

import devops.ecom.pageeventconsumerservice.entities.PageEventSearchByKeyword;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PageEventSearchByKeywordRepo extends MongoRepository<PageEventSearchByKeyword, String> {
}
