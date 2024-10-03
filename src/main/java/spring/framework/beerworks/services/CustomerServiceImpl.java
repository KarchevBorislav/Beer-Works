package spring.framework.beerworks.services;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
                .customerName("John Doe")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        Customer customer2 = Customer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName("John Snow")
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

    @Override
    public Customer saveNewCustomer(Customer customer) {
        Customer newCustomer = Customer.builder()
                .customerName(customer.getCustomerName())
                .id(UUID.randomUUID())
                .version(customer.getVersion())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();


        customerMap.put(newCustomer.getId(), newCustomer);

        return newCustomer;
    }

    @Override
    public void updateCustomerById(UUID customerId, Customer customer) {

        Customer existing = customerMap.get(customerId);
        existing.setCustomerName(customer.getCustomerName());


        customerMap.put(existing.getId(), existing);
    }

    @Override
    public void deleteById(UUID customerId) {

        customerMap.remove(customerId);
    }

    @Override
    public void patchCustomerById(UUID customerId, Customer customer) {
        Customer existing = customerMap.get(customerId);

        if (StringUtils.hasText(existing.getCustomerName())) {
            existing.setCustomerName(customer.getCustomerName());
        }
        if (existing.getId() != null){

            existing.setId(customer.getId());
        }
        if (existing.getVersion() != null){
            existing.setVersion(customer.getVersion());
        }
        




    }
}
