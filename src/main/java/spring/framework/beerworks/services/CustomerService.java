package spring.framework.beerworks.services;

import spring.framework.beerworks.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    Customer getCustomerById(UUID customerId);

    List<Customer> getCustomerList();

    Customer saveNewCustomer(Customer customer);

    void updateCustomerById(UUID customerId, Customer customer);
}
