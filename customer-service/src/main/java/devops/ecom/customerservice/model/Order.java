package devops.ecom.customerservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
public class Order {
    @Id
    private String id;
    private String customerId;
    private List<ShoppingCartItem> items;
    @DBRef
    private Payment payment;
    private DeliveryData deliveryData;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
