package devops.ecom.productservice.dao.entities;

import devops.ecom.productservice.dao.enums.PageEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageEventClickProduct {
    @Id
    private String pageEventId;
    private String userId;
    private Date date;
    private PageEventType type;
    private String productName;
}
