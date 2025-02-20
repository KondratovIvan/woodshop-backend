package devops.ecom.customerservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDataDto {

    private String name;
    private String surname;
    private String city;
    private String telephoneNumber;
    private Long postOfficeNumber;

}
