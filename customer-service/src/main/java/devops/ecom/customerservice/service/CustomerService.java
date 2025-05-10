package devops.ecom.customerservice.service;

import devops.ecom.customerservice.exceptions.CustomerNotFoundException;
import devops.ecom.customerservice.model.Customer;

public interface CustomerService {
    Customer createCustomer(Customer customer);

    void syncKeycloakUsers();

    void deleteCustomer(String customerId);

    Customer updateCustomer(String customerId, Customer customerDto) throws CustomerNotFoundException;

}
