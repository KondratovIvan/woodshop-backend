package devops.ecom.customerservice.service;

import devops.ecom.customerservice.model.Customer;

public interface CustomerService {
    Customer createCustomer(Customer customer);

    void syncKeycloakUsers();
}
