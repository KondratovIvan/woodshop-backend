package devops.ecom.customerservice.web;

import devops.ecom.customerservice.exceptions.CustomerNotFoundException;
import devops.ecom.customerservice.model.AddItemRequest;
import devops.ecom.customerservice.model.Customer;
import devops.ecom.customerservice.model.ShoppingCart;
import devops.ecom.customerservice.service.CustomerService;
import devops.ecom.customerservice.service.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
public class CustomerController {
    private final ShoppingCartService cartService;
    private final CustomerService customerService;

    public CustomerController(ShoppingCartService cartService, CustomerService customerService) {
        this.cartService = cartService;
        this.customerService = customerService;
    }

    @GetMapping("/customers/{customerId}/shoppingCart")
    public ShoppingCart getShoppingCart(@PathVariable String customerId) {
        try {
            return cartService.getCartForCustomer(customerId);
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ShoppingCart addProductToCart(@RequestBody AddItemRequest addItemRequest) {
        try {
            return this.cartService.addItemToCart(addItemRequest);
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{customerId}/{productId}/increase")
    public ShoppingCart increaseProductQuantity(@PathVariable String customerId,
                                                @PathVariable String productId) {
        try {
            return cartService.increaseItemQuantity(customerId, productId);
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{customerId}/{productId}/decrease")
    public ShoppingCart decreaseProductQuantity(@PathVariable String customerId,
                                                @PathVariable String productId) {
        try {
            return this.cartService.decreaseItemQuantity(customerId, productId);
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("{customerId}/{productId}")
    public ShoppingCart deleteItemFromCart(@PathVariable String customerId, @PathVariable String productId) {
        try {
            return this.cartService.removeItemFromCart(customerId, productId);
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/customers/{customerId}")
    public Customer updateCustomer(@PathVariable String customerId, @RequestBody Customer customerDto) throws CustomerNotFoundException {
        return customerService.updateCustomer(customerId, customerDto);
    }

    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/customers/{customerId}/shoppingCart")
    public ShoppingCart clearShoppingCart(@PathVariable String customerId) {
        try {
            return cartService.clearCart(customerId);
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
