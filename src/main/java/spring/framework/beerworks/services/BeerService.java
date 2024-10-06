package spring.framework.beerworks.services;

import org.springframework.stereotype.Service;
import spring.framework.beerworks.model.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService  {

    List<Beer> beerList();

   Optional<Beer>  getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void updateBeerById(UUID beerId, Beer beer);

    void deleteById(UUID beerId);

    void patchBeerById(UUID beerId, Beer beer);
}
