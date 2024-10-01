package spring.framework.beerworks.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spring.framework.beerworks.model.Beer;
import spring.framework.beerworks.model.BeerStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {




    @Override
    public Beer getBeerById(UUID id) {


        log.debug("Called GetBeerById in Service");
        return Beer.builder().id(id)
                .version(1)
                .beerName("Erdinger")
                .beerStyle(BeerStyle.HEFE_WEIZEN)
                .price(new BigDecimal("22.10"))
                .quantityOnHand(20)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }


}
