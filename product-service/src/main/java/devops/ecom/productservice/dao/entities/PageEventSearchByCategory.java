package devops.ecom.productservice.dao.entities;

import devops.ecom.productservice.dao.enums.PageEventType;
import devops.ecom.productservice.dao.enums.ProductCategory;
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
public class PageEventSearchByCategory {
    @Id
    private String pageEventId;
    private String userId;
    private Date date;
    private PageEventType type;
    private ProductCategory category;
}
