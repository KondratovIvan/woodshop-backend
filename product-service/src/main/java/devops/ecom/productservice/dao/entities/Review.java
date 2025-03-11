package devops.ecom.productservice.dao.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
public class Review {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String reviewText;
    private Integer rating;
    private LocalDateTime date;
    private String productId;

}
