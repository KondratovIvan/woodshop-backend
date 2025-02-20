package devops.ecom.customerservice.web.model;

import devops.ecom.customerservice.model.ShoppingCartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private String customerId;
    private List<ShoppingCartItem> items;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private DeliveryDataDto deliveryData;
    private PaymentDto payment;

}
