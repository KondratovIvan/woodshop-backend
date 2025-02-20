package devops.ecom.customerservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class DeliveryData {

    @Id
    private String id;

    private String orderId;
    private String name;
    private String surname;
    private String city;
    private String telephoneNumber;
    private Long postOfficeNumber;

}
