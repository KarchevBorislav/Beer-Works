package spring.framework.beerworks.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.beerworks.Exceptions.NotFoundException;
import spring.framework.beerworks.entities.Customer;
import spring.framework.beerworks.model.CustomerDTO;
import spring.framework.beerworks.repositories.CustomerRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerController customerController;

    @Test
    void testGetById() {

        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO customerById = customerController.getCustomerById(customer.getId());

        assertThat(customerById).isNotNull();
    }

    @Test
    void testGetByIdNotFound() {
        assertThrows(NotFoundException.class, () ->
                customerController.getCustomerById(UUID.randomUUID()));
    }

    @Test
    void testListAll() {
        List<CustomerDTO> customerDTOS = customerController.customerList();
        assertThat(customerDTOS.size()).isEqualTo(2);

    }

    @Transactional
    @Rollback
    @Test
    void testAllEmptyList() {
        customerRepository.deleteAll();

        List<CustomerDTO> customerDTOS = customerController.customerList();
        assertThat(customerDTOS.size()).isEqualTo(0);

    }
}