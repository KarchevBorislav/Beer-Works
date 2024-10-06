package spring.framework.beerworks.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import spring.framework.beerworks.entities.Beer;
import spring.framework.beerworks.entities.Customer;
import spring.framework.beerworks.model.BeerStyle;
import spring.framework.beerworks.model.CustomerDTO;
import spring.framework.beerworks.repositories.BeerRepository;
import spring.framework.beerworks.repositories.CustomerRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {


        loadBeerData();
        loadCustomerData();
    }

    private void loadCustomerData() {


        if (customerRepository.count() == 0) {

            Customer customer1 = Customer.builder()
                    .id(UUID.randomUUID())
                    .customerName("John Doe")
                    .version(1)
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

            customerRepository.saveAll(Arrays.asList(customer1, customer2));

        }

    }

    private void loadBeerData() {

        if (beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Erdinger Hell")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("1")
                    .price(new BigDecimal("20.20"))
                    .quantityOnHand(20)
                    .createDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();


            Beer beer2 = Beer.builder()

                    .beerName("Pilsen Urqel")
                    .beerStyle(BeerStyle.PILSNER)
                    .upc("2")
                    .price(new BigDecimal("20.20"))
                    .quantityOnHand(20)
                    .createDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Guinness")
                    .beerStyle(BeerStyle.STOUT)
                    .upc("3")
                    .price(new BigDecimal("20.20"))
                    .quantityOnHand(20)
                    .createDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }


    }
}
