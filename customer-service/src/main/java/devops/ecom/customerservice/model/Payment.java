package devops.ecom.customerservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document
public class Payment {

    @Id
    private String id;

    private String orderId;
    private BigDecimal amount;
    private String currency;
    private Boolean byCreditCard;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
