package devops.ecom.customerservice.service;

import devops.ecom.customerservice.exceptions.CustomerNotFoundException;
import devops.ecom.customerservice.model.Customer;
import devops.ecom.customerservice.model.ShoppingCart;
import devops.ecom.customerservice.repos.CustomerRepo;
import devops.ecom.customerservice.repos.ShoppingCartRepo;
import lombok.Data;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Data
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepo customerRepo;
    private ShoppingCartRepo shoppingCartRepo;

    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${admin.client-id}")
    private String adminClientId;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    public CustomerServiceImpl(CustomerRepo customerRepo, ShoppingCartRepo shoppingCartRepo) {
        this.customerRepo = customerRepo;
        this.shoppingCartRepo = shoppingCartRepo;
    }

    private String getAdminAccessToken() {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakUrl)
                .realm("master") // Админ-клиенты в Keycloak всегда живут в master-реалме!
                .clientId("admin-cli") // Встроенный клиент Keycloak
                .username(adminUsername)
                .password(adminPassword)
                .grantType("password")
                .build();

        AccessTokenResponse tokenResponse = keycloak.tokenManager().getAccessToken();
        return tokenResponse.getToken();
    }

    @Override
    public Customer createCustomer(Customer customer) {
        ShoppingCart cart = ShoppingCart.builder()
                .id(UUID.randomUUID().toString())
                .items(new ArrayList<>())
                .customerId(customer.getCustomerId()).build();
        ShoppingCart insertedCart = this.shoppingCartRepo.insert(cart);
        customer.setShoppingCart(insertedCart);
        return this.customerRepo.insert(customer);
    }

    @Override
    public void syncKeycloakUsers() {
        String accessToken = getAdminAccessToken();

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakUrl)
                .realm(keycloakRealm)
                .clientId(adminClientId)
                .authorization(accessToken)
                .build();

        List<UserRepresentation> keycloakUsers = keycloak.realm(keycloakRealm).users().list();
        for (UserRepresentation keycloakUser : keycloakUsers) {
            Optional<Customer> optionalUser = customerRepo.findById(keycloakUser.getId());
            Customer customer;
            if (optionalUser.isPresent()) {
                // updating the user
                customer = optionalUser.get();
            } else {
                // initializing user info
                customer = new Customer();
                ShoppingCart cart = ShoppingCart.builder()
                        .id(UUID.randomUUID().toString())
                        .items(new ArrayList<>())
                        .customerId(keycloakUser.getId()).build();
                ShoppingCart insertedCart = this.shoppingCartRepo.insert(cart);
                customer.setShoppingCart(insertedCart);
            }
            customer.setEmail(keycloakUser.getEmail());
            customer.setCustomerId(keycloakUser.getId());
            customer.setFirstname(keycloakUser.getFirstName());
            customer.setLastname(keycloakUser.getLastName());
            customerRepo.save(customer);
        }
    }

    @Override
    public void deleteCustomer(String customerId) {
        String accessToken = getAdminAccessToken();

        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakUrl)
                .realm(keycloakRealm)
                .clientId(adminClientId)
                .authorization(accessToken)
                .build();

        keycloak.realm(keycloakRealm).users().delete(customerId);

        customerRepo.deleteById(customerId);
    }

    @Override
    public Customer updateCustomer(String customerId, Customer customerDto) throws CustomerNotFoundException {
        Customer existingCustomer = customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + customerId));

        String accessToken = getAdminAccessToken();
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakUrl)
                .realm(keycloakRealm)
                .clientId(adminClientId)
                .authorization(accessToken)
                .build();

        UserRepresentation currentUser = keycloak.realm(keycloakRealm)
                .users()
                .get(customerId)
                .toRepresentation();

        // Обновляем только разрешенные поля
        if (customerDto.getEmail() != null) {
            currentUser.setEmail(customerDto.getEmail());
            existingCustomer.setEmail(customerDto.getEmail());
        }
        if (customerDto.getFirstname() != null) {
            currentUser.setFirstName(customerDto.getFirstname());
            existingCustomer.setFirstname(customerDto.getFirstname());
        }
        if (customerDto.getLastname() != null) {
            currentUser.setLastName(customerDto.getLastname());
            existingCustomer.setLastname(customerDto.getLastname());
        }

        // Обновляем пользователя в Keycloak
        keycloak.realm(keycloakRealm)
                .users()
                .get(customerId)
                .update(currentUser);

        // Сохраняем изменения в базе данных
        return customerRepo.save(existingCustomer);
    }
}
