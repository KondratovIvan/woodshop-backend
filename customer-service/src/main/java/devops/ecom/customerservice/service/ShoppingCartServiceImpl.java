package devops.ecom.customerservice.service;

import devops.ecom.customerservice.RestClients.ProductRestClient;
import devops.ecom.customerservice.exceptions.CustomerNotFoundException;
import devops.ecom.customerservice.model.*;
import devops.ecom.customerservice.repos.CustomerRepo;
import devops.ecom.customerservice.repos.ShoppingCartRepo;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private CustomerRepo customerRepo;
    private ShoppingCartRepo shoppingCartRepo;
    private ProductRestClient productRestClient;

    public ShoppingCartServiceImpl(CustomerRepo customerRepo, ShoppingCartRepo shoppingCartRepo, ProductRestClient productRestClient) {
        this.customerRepo = customerRepo;
        this.shoppingCartRepo = shoppingCartRepo;
        this.productRestClient = productRestClient;
    }

    @Override
    public ShoppingCart getCartForCustomer(String customerId) throws CustomerNotFoundException {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + customerId));
        return customer.getShoppingCart();
    }

    @Override
    public ShoppingCartItem createItem(AddItemRequest addItemRequest) {
        int quantity = addItemRequest.getQuantity() > 0 ? addItemRequest.getQuantity() : 1;
        Product p = this.productRestClient.getProduct(addItemRequest.getProductId(), getToken());
        p.setPickedColor(addItemRequest.getPickedColor());
        return ShoppingCartItem.builder()
                .product(p)
                .quantity(quantity)
                .build();
    }

    @Override
    public ShoppingCart addItemToCart(AddItemRequest addItemRequest) throws CustomerNotFoundException {
        Customer customer = this.customerRepo.findById(addItemRequest.getCustomerId()).orElseThrow(()
                -> new CustomerNotFoundException("customer fo id *" + addItemRequest.getCustomerId() + "not found"));
        ShoppingCart shoppingCart = customer.getShoppingCart();
        ShoppingCartItem item = checkProductInCart(shoppingCart, addItemRequest.getProductId());
        if (item != null) {
            return updateItemInCart(item, addItemRequest, shoppingCart);
        } else {
            item = createItem(addItemRequest);
            shoppingCart.getItems().add(item);
            ShoppingCart savedCard = this.shoppingCartRepo.save(shoppingCart);
            customer.setShoppingCart(savedCard);
            this.customerRepo.save(customer);
            return savedCard;
        }
    }

    @Override
    public ShoppingCart removeItemFromCart(String customerId, String productId) throws CustomerNotFoundException {
        Customer customer = this.customerRepo.findById(customerId).orElseThrow(()
                -> new CustomerNotFoundException("customer fo id *" + customerId + "not found"));
        ShoppingCart shoppingCart = customer.getShoppingCart();
        ShoppingCartItem item = checkProductInCart(shoppingCart, productId);
        if (item != null) {
            shoppingCart.getItems().remove(item);
            ShoppingCart savedCard = this.shoppingCartRepo.save(shoppingCart);
            customer.setShoppingCart(savedCard);
            this.customerRepo.save(customer);
            return savedCard;
        } else throw new RuntimeException("product not found in shoppingCart to delete");

    }

    @Override
    public ShoppingCart updateItemInCart(ShoppingCartItem item, AddItemRequest addItemRequest, ShoppingCart cart) {
        int toAdd = addItemRequest.getQuantity() > 0 ? addItemRequest.getQuantity() : 1;
        int newQty = item.getQuantity() + toAdd;
        int index = cart.getItems().indexOf(item);
        cart.getItems().get(index).setQuantity(newQty);
        cart.getItems().get(index).getProduct().setPickedColor(addItemRequest.getPickedColor());
        return shoppingCartRepo.save(cart);

    }

    @Override
    public ShoppingCart decreaseItemQuantity(String customerId, String productId) throws CustomerNotFoundException {
        Customer customer = this.customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + customerId + " not found"));
        ShoppingCart shoppingCart = customer.getShoppingCart();
        ShoppingCartItem item = checkProductInCart(shoppingCart, productId);
        if (item == null) {
            throw new RuntimeException("Product not found in shopping cart");
        }

        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
        } else {
            shoppingCart.getItems().remove(item);
        }
        ShoppingCart savedCart = this.shoppingCartRepo.save(shoppingCart);
        customer.setShoppingCart(savedCart);
        this.customerRepo.save(customer);
        return savedCart;
    }

    @Override
    public ShoppingCart increaseItemQuantity(String customerId, String productId) throws CustomerNotFoundException {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + customerId + " not found"));
        ShoppingCart shoppingCart = customer.getShoppingCart();
        ShoppingCartItem item = checkProductInCart(shoppingCart, productId);
        if (item == null) {
            throw new RuntimeException("Product not found in shopping cart");
        }
        item.setQuantity(item.getQuantity() + 1);
        ShoppingCart savedCart = shoppingCartRepo.save(shoppingCart);
        customer.setShoppingCart(savedCart);
        customerRepo.save(customer);
        return savedCart;
    }

    private ShoppingCartItem checkProductInCart(ShoppingCart cart, String productId) {
        for (ShoppingCartItem item : cart.getItems()) {
            if (productId.equals(item.getProduct().getProductId()))
                return item;
        }
        return null;

    }

    private String getToken() {
        KeycloakSecurityContext context = (KeycloakSecurityContext) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String token = "bearer " + context.getTokenString();
        return token;
    }
}
