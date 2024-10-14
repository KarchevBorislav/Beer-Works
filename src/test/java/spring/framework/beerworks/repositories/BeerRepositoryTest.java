package spring.framework.beerworks.repositories;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import spring.framework.beerworks.bootstrap.BootstrapData;
import spring.framework.beerworks.entities.Beer;
import spring.framework.beerworks.model.BeerStyle;
import spring.framework.beerworks.services.BeerCsvServiceImpl;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
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
        assertEquals(2414, size);
    }

    @Test
    void testSaveBeerNameToLong() {
        assertThrows(ConstraintViolationException.class,()->{
            Beer savedBeer = beerRepository.save(Beer.builder().beerName("MyBearNameJustToLongasasasafdffdfddsdasdsadasczcxzczcxzcxzcxzcdsadasfdfdfdfd")
                    .beerStyle(BeerStyle.STOUT)
                    .upc("1211")
                    .price(new BigDecimal("1.1"))
                    .build());
            beerRepository.flush();

        });

    }


    @Test
    void testGetBeerListByNameIgnoreCase() {
        Page<Beer> beerList = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", null);
    assertThat(beerList.getContent().size()).isEqualTo(336);

    }
}