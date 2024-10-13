package spring.framework.beerworks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.framework.beerworks.entities.Beer;
import spring.framework.beerworks.model.BeerStyle;

import java.util.List;
import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

    List<Beer> findAllByBeerNameIsLikeIgnoreCase(String beerName);
    List<Beer> findAllByBeerStyle(BeerStyle beerStyle);

    List<Beer>findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(String beerName,BeerStyle beerStyle);
}
