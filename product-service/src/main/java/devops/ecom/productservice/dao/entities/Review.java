package devops.ecom.productservice.dao.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Review {

    @Id
    private String id;

    private Integer rate;
    private String comment;
    private String author;
    private String productId;

}
