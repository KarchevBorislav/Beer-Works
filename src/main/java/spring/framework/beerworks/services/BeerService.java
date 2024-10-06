package spring.framework.beerworks.services;

import spring.framework.beerworks.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService  {

    List<BeerDTO> beerList();

   Optional<BeerDTO>  getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    void updateBeerById(UUID beerId, BeerDTO beerDTO);

    void deleteById(UUID beerId);

    void patchBeerById(UUID beerId, BeerDTO beerDTO);
}
