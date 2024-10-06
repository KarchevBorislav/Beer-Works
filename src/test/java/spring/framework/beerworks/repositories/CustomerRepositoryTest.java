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


        Customer myCustomer = customerRepository.save(Customer.builder().customerName("My Customer").build());

        long size = customerRepository.count();

        assertEquals(1, size);
        assertThat(myCustomer).isNotNull();
        assertThat(myCustomer.getId()).isNotNull();


    }

}