package devops.ecom.customerservice.web;

import devops.ecom.customerservice.model.Order;
import devops.ecom.customerservice.service.OrderService;
import devops.ecom.customerservice.web.model.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody OrderDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable String id) {
        return orderService.getOrderById(id);
    }

}
