package spring.framework.beerworks.services;

import org.springframework.stereotype.Service;
import spring.framework.beerworks.model.Customer;

import java.time.LocalDateTime;
import java.util.*;

@Service

public class CustomerServiceImpl implements CustomerService {


    private Map<UUID, Customer> customerMap;

    public CustomerServiceImpl() {
        customerMap = new HashMap<>();


        Customer customer1 = Customer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customName("John Doe")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customName("John Snow")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
    }


    @Override
    public Customer getCustomerById(UUID customerId) {


        return customerMap.get(customerId);
    }


    @Override
    public List<Customer> getCustomerList(){
        return new ArrayList<Customer>(customerMap.values());
    }


}
