package spring.framework.beerworks.services;

import org.springframework.data.domain.Page;
import spring.framework.beerworks.model.BeerDTO;
import spring.framework.beerworks.model.BeerStyle;

import java.util.Optional;
import java.util.UUID;

public interface BeerService  {

    Page<BeerDTO> beerList(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);

   Optional<BeerDTO>  getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beerDTO);

    Boolean deleteById(UUID beerId);

    Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beerDTO);


}
