package spring.framework.beerworks.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.beerworks.entities.Beer;
import spring.framework.beerworks.entities.BeerOrder;
import spring.framework.beerworks.entities.Customer;


@SpringBootTest
class BeerOrderRepositoryTest {


    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;


    @Autowired
    BeerRepository beerRepository;

    Customer testCustomer;
    Beer testBeer;


    @BeforeEach
    void setUp() {
        testCustomer = customerRepository.findAll().get(0);
        testBeer = beerRepository.findAll().get(0);

    }

    @Transactional
    @Test
    void testBeerOrders() {
        BeerOrder testBeerOrder = BeerOrder.builder().customerRef("Test")
                .customer(testCustomer)
                .build();

        BeerOrder save = beerOrderRepository.save(testBeerOrder);

    }
}