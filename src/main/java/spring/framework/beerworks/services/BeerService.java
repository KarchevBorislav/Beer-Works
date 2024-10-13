package spring.framework.beerworks.services;

import spring.framework.beerworks.model.BeerDTO;
import spring.framework.beerworks.model.BeerStyle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService  {

    List<BeerDTO> beerList(String beerName, BeerStyle beerStyle);

   Optional<BeerDTO>  getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beerDTO);

    Boolean deleteById(UUID beerId);

    Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beerDTO);


}
