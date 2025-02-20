package devops.ecom.customerservice.web.model;

import devops.ecom.customerservice.model.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private BigDecimal amount;
    private String currency;
    private PaymentType method;

}
