package spring.framework.beerworks.repositories;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spring.framework.beerworks.entities.Beer;
import spring.framework.beerworks.model.BeerStyle;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {


    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer() {

        Beer savedBeer = beerRepository.save(Beer.builder().beerName("MyBear")
                .beerStyle(BeerStyle.STOUT)
                .upc("1211")
                .price(new BigDecimal("1.1"))
                .build());

        long size = beerRepository.count();


        beerRepository.flush();
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
        assertEquals(1, size);
    }    @Test
    void testSaveBeerNameToLong() {
        assertThrows(ConstraintViolationException.class,()->{
            Beer savedBeer = beerRepository.save(Beer.builder().beerName("MyBearNameJustToLong")
                    .beerStyle(BeerStyle.STOUT)
                    .upc("1211")
                    .price(new BigDecimal("1.1"))
                    .build());
            beerRepository.flush();

        });





    }


}