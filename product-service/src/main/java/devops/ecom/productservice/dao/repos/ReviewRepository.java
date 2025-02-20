package devops.ecom.productservice.dao.repos;

import devops.ecom.productservice.dao.entities.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RestResource
public interface ReviewRepository extends MongoRepository<devops.ecom.productservice.dao.entities.Review, String> {

    List<Review> findAllByProductId(String productId);

}
