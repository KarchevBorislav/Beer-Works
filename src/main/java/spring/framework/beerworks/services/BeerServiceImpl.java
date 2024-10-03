package spring.framework.beerworks.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import spring.framework.beerworks.model.Beer;
import spring.framework.beerworks.model.BeerStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, Beer> beerMap;


    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();


        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Erdinger Hell")
                .beerStyle(BeerStyle.LAGER)
                .upc("1")
                .price(new BigDecimal("20.20"))
                .quantityOnHand(20)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();


        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Pilsen Urqel")
                .beerStyle(BeerStyle.PILSNER)
                .upc("2")
                .price(new BigDecimal("20.20"))
                .quantityOnHand(20)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Guinness")
                .beerStyle(BeerStyle.STOUT)
                .upc("3")
                .price(new BigDecimal("20.20"))
                .quantityOnHand(20)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);

    }

    @Override
    public List<Beer> beerList() {
        return new ArrayList<Beer>(beerMap.values());
    }


    @Override
    public Beer getBeerById(UUID id) {


        log.debug("Called GetBeerById in Service");
        return beerMap.get(id);
    }

    @Override
    public Beer saveNewBeer(Beer beer) {
        Beer savedBeer = Beer.builder()
                .id(UUID.randomUUID())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .build();


        beerMap.put(savedBeer.getId(), savedBeer);

        return savedBeer;
    }

    @Override
    public void updateBeerById(UUID beerId, Beer beer) {
        Beer existing = beerMap.get(beerId);
      existing.setBeerName(beer.getBeerName());
      existing.setQuantityOnHand(beer.getQuantityOnHand());
      existing.setUpc(beer.getUpc());
      existing.setPrice(beer.getPrice());

      beerMap.put(existing.getId(), existing);

    }

    @Override
    public void deleteById(UUID beerId) {
        beerMap.remove(beerId);

    }

    @Override
    public void patchBeerById(UUID beerId, Beer beer)   {
        Beer existing = beerMap.get(beerId);

        if (StringUtils.hasText(beer.getBeerName())){
            existing.setBeerName(beer.getBeerName());
        }
        if (beer.getBeerStyle() != null){
            existing.setBeerStyle(beer.getBeerStyle());
        }
        if (beer.getPrice() != null){
            existing.setPrice(beer.getPrice());
        }
        if (beer.getQuantityOnHand() != null){
            existing.setQuantityOnHand(beer.getQuantityOnHand());

        }
        if (StringUtils.hasText(beer.getUpc())){
            existing.setUpc(beer.getUpc());
        }

    }
}
