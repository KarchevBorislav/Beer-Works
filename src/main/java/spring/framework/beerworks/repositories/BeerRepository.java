package spring.framework.beerworks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.framework.beerworks.entities.Beer;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
