package spring.framework.beerworks.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.beerworks.Exceptions.NotFoundException;
import spring.framework.beerworks.entities.Beer;
import spring.framework.beerworks.entities.Customer;
import spring.framework.beerworks.mappers.CustomerMapper;
import spring.framework.beerworks.model.BeerDTO;
import spring.framework.beerworks.model.CustomerDTO;
import spring.framework.beerworks.repositories.CustomerRepository;
import spring.framework.beerworks.services.CustomerService;

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
    @Autowired
    CustomerService customerService;
    @Autowired
    CustomerMapper customerMapper;

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

    @Test
    void testGetCustomerById() {
        Customer customer = customerRepository.findAll().get(0);

        customerController.getCustomerById(customer.getId());
        assertThat(customer).isNotNull();

    }

    @Test
    void testGetCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewCustomer(){
        CustomerDTO testCustomer= CustomerDTO.builder().customerName("Test Customer").build();
        ResponseEntity responseEntity = customerController.handlePost(testCustomer);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] location = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID id = UUID.fromString(location[4]);


        Customer customer = customerRepository.findById(id).get();
        assertThat(customer).isNotNull();
    }

}