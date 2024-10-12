package spring.framework.beerworks.bootstrap;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import spring.framework.beerworks.entities.Beer;
import spring.framework.beerworks.entities.Customer;
import spring.framework.beerworks.model.BeerCSVRecord;
import spring.framework.beerworks.model.BeerStyle;
import spring.framework.beerworks.model.CustomerDTO;
import spring.framework.beerworks.repositories.BeerRepository;
import spring.framework.beerworks.repositories.CustomerRepository;
import spring.framework.beerworks.services.BeerCsvService;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCsvService beerCsvService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        loadCsvData();
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

    private void loadCsvData() throws FileNotFoundException {

        if (beerRepository.count() < 10) {
            File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
            List<BeerCSVRecord> beerCSVRecordList = beerCsvService.convertCSV(file);


            beerCSVRecordList.forEach(beerCSVRecord -> {
                BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                            BeerStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.HEFE_WEIZEN;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };
                beerRepository.save(Beer.builder()
                        .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
                        .beerStyle(beerStyle)
                        .price(BigDecimal.TEN)
                        .upc(beerCSVRecord.getRow().toString())
                        .quantityOnHand(beerCSVRecord.getCount())
                        .build());
            });
        }
    }
}
