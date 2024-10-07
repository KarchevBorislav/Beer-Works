package spring.framework.beerworks.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.beerworks.Exceptions.NotFoundException;
import spring.framework.beerworks.entities.Beer;
import spring.framework.beerworks.model.BeerDTO;
import spring.framework.beerworks.repositories.BeerRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;


    @Test
    void testListBeers() {
        List<BeerDTO> beerDTOS = beerController.beerList();


        assertThat(beerDTOS.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> beerDTOS = beerController.beerList();

        assertThat(beerDTOS.size()).isEqualTo(0);
    }

    @Test
    void testGetBeerById(){
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO beerByIdDTO = beerController.getBeerById(beer.getId());

        assertThat(beerByIdDTO).isNotNull();
    }

    @Test
    void testBeerIdNotFound(){
        assertThrows(NotFoundException.class, () -> beerController.getBeerById(UUID.randomUUID()));

    }
}