package devops.ecom.customerservice.web;

import devops.ecom.customerservice.model.Order;
import devops.ecom.customerservice.service.OrderService;
import devops.ecom.customerservice.web.model.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/orders")
@CrossOrigin(origins = "*")
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

    @GetMapping("/customer/{customerId}")
    public List<Order> getAllOrdersByCustomerId(@PathVariable String customerId) {
        return orderService.getAllOrdersByCustomerId(customerId);
    }

}
