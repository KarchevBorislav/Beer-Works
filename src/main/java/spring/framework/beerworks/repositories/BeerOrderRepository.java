package spring.framework.beerworks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.framework.beerworks.entities.Beer;
import spring.framework.beerworks.entities.BeerOrder;

import java.util.UUID;

public interface BeerOrderRepository  extends JpaRepository<BeerOrder, UUID> {
}
