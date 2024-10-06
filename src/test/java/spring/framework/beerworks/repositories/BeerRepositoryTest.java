package spring.framework.beerworks.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import spring.framework.beerworks.entities.Beer;
import spring.framework.beerworks.model.BeerStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {


    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeer(){

        Beer savedBeer = beerRepository.save(Beer.builder().beerName("MyBear").build());

        long size = beerRepository.count();


        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
        assertEquals(1, size);
    }



}