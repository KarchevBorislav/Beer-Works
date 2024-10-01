package spring.framework.beerworks.services;

import org.springframework.stereotype.Service;
import spring.framework.beerworks.model.Beer;

import java.util.UUID;

public interface BeerService  {

    Beer getBeerById(UUID id);
}
