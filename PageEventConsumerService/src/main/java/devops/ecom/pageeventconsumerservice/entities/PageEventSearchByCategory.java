package devops.ecom.pageeventconsumerservice.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class PageEventSearchByCategory {
    @Id
    private String pageEventId;
    private String userId;
    private Date date;
    private String type;
    private String category;
}
