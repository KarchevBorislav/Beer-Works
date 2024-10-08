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
import spring.framework.beerworks.mappers.BeerMapper;
import spring.framework.beerworks.model.BeerDTO;
import spring.framework.beerworks.repositories.BeerRepository;
import spring.framework.beerworks.services.BeerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerService beerService;

    @Autowired
    BeerMapper beerMapper;


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
    void testGetBeerById() {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO beerByIdDTO = beerController.getBeerById(beer.getId());

        assertThat(beerByIdDTO).isNotNull();
    }

    @Test
    void testBeerIdNotFound() {
        assertThrows(NotFoundException.class, () -> beerController.getBeerById(UUID.randomUUID()));

    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewBeer() {
        BeerDTO testBeer = BeerDTO.builder().beerName("Test Beer").build();
        ResponseEntity responseEntity = beerController.handlePost(testBeer);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] location = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID id = UUID.fromString(location[4]);
        Beer beer = beerRepository.findById(id).get();
        assertThat(beer).isNotNull();


    }


    @Transactional
    @Rollback
    @Test
    void testUpdateExistingBeer() {
        Beer beer = beerRepository.findAll().get(0);


        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String beerName = "Updated Beer";
        beerDTO.setBeerName(beerName);

        ResponseEntity responseEntity = beerController.updateById(beer.getId(), beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer byId = beerRepository.findById(beer.getId()).get();

        assertThat(byId.getBeerName()).isEqualTo(beerName);

    }

    @Test
    void testUpdateNotFound() {

        assertThrows(NotFoundException.class, () -> beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build()));
    }


    @Transactional
    @Rollback
    @Test
    void testDeleteBeerByIdFound() {
        Beer beer = beerRepository.findAll().get(0);
        ResponseEntity responseEntity = beerController.deleteById(beer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(beerRepository.findById(beer.getId())).isEmpty();


    }

    @Transactional
    @Rollback
    @Test
    void testDeleteBeerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> beerController.deleteById(UUID.randomUUID()));


    }
}