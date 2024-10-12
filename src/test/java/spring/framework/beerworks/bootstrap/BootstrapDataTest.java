package spring.framework.beerworks.bootstrap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spring.framework.beerworks.repositories.BeerRepository;
import spring.framework.beerworks.repositories.CustomerRepository;
import spring.framework.beerworks.services.BeerCsvService;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerCsvService beerCsvService;

    BootstrapData bootstrapData;


    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(beerRepository,customerRepository,beerCsvService);
    }

    @Test
    void testRun() throws Exception {

        bootstrapData.run(  null);

        assertThat(beerRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(2);
    }
}