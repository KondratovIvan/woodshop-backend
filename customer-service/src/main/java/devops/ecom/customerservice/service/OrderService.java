package devops.ecom.customerservice.service;

import devops.ecom.customerservice.model.DeliveryData;
import devops.ecom.customerservice.model.Order;
import devops.ecom.customerservice.model.Payment;
import devops.ecom.customerservice.model.ShoppingCartItem;
import devops.ecom.customerservice.repos.DeliveryDataRepository;
import devops.ecom.customerservice.repos.OrderRepository;
import devops.ecom.customerservice.repos.PaymentRepository;
import devops.ecom.customerservice.repos.ShoppingCartItemRepository;
import devops.ecom.customerservice.web.model.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final DeliveryDataRepository deliveryDataRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;

    @Transactional
    public Order createOrder(OrderDto orderDto) {
        List<ShoppingCartItem> items = orderDto.getItems();

        BigDecimal totalAmount = items.stream()
                .map(item -> item.getProduct().getProductPrice().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setCustomerId(orderDto.getCustomerId());
        order.setItems(items);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        order = orderRepository.save(order);

        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setAmount(totalAmount);
        payment.setCurrency(orderDto.getPayment().getCurrency());
        payment.setMethod(orderDto.getPayment().getMethod());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());

        payment = paymentRepository.save(payment);
        order.setPayment(payment);

        DeliveryData deliveryData = new DeliveryData();
        deliveryData.setOrderId(order.getId());
        deliveryData.setName(orderDto.getDeliveryData().getName());
        deliveryData.setSurname(orderDto.getDeliveryData().getSurname());
        deliveryData.setCity(orderDto.getDeliveryData().getCity());
        deliveryData.setTelephoneNumber(orderDto.getDeliveryData().getTelephoneNumber());
        deliveryData.setPostOfficeNumber(orderDto.getDeliveryData().getPostOfficeNumber());

        deliveryData = deliveryDataRepository.save(deliveryData);
        order.setDeliveryData(deliveryData);

        return orderRepository.save(order);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }

}
