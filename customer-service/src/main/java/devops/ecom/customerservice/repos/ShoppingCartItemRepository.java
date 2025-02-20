package devops.ecom.customerservice.repos;

import devops.ecom.customerservice.model.ShoppingCartItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartItemRepository extends MongoRepository<ShoppingCartItem, String> {
    List<ShoppingCartItem> findByIdIn(List<String> ids);

}
