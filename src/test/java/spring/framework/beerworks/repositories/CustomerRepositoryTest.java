package spring.framework.beerworks.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spring.framework.beerworks.entities.Customer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test

    public void testSaveCustomer() {


        Customer customer = customerRepository.save(Customer.builder()
                .customerName("New Name")
                .build());

        assertThat(customer.getId()).isNotNull();


    }

}