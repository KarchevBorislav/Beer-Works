package spring.framework.beerworks.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import spring.framework.beerworks.model.BeerDTO;
import spring.framework.beerworks.model.BeerStyle;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, BeerDTO> beerMap;


    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();


        BeerDTO beerDTO1 = BeerDTO.builder()
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


        BeerDTO beerDTO2 = BeerDTO.builder()
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

        BeerDTO beerDTO3 = BeerDTO.builder()
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

        beerMap.put(beerDTO1.getId(), beerDTO1);
        beerMap.put(beerDTO2.getId(), beerDTO2);
        beerMap.put(beerDTO3.getId(), beerDTO3);

    }

    @Override
    public List<BeerDTO> beerList(String beerName) {
        return new ArrayList<BeerDTO>(beerMap.values());
    }


    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {


        log.debug("Called GetBeerById in Service");
        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        BeerDTO savedBeerDTO = BeerDTO.builder()
                .id(UUID.randomUUID())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beerDTO.getBeerName())
                .beerStyle(beerDTO.getBeerStyle())
                .quantityOnHand(beerDTO.getQuantityOnHand())
                .upc(beerDTO.getUpc())
                .price(beerDTO.getPrice())
                .build();


        beerMap.put(savedBeerDTO.getId(), savedBeerDTO);

        return savedBeerDTO;
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beerDTO) {
        BeerDTO existing = beerMap.get(beerId);
        existing.setBeerName(beerDTO.getBeerName());
        existing.setQuantityOnHand(beerDTO.getQuantityOnHand());
        existing.setUpc(beerDTO.getUpc());
        existing.setPrice(beerDTO.getPrice());

        beerMap.put(existing.getId(), existing);

        return Optional.of(existing);
    }

    @Override
    public Boolean deleteById(UUID beerId) {

        if (beerMap.containsKey(beerId)) {
            beerMap.remove(beerId);
            return true;
        }

        return false;

    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beerDTO) {
        BeerDTO existing = beerMap.get(beerId);

        if (StringUtils.hasText(beerDTO.getBeerName())) {
            existing.setBeerName(beerDTO.getBeerName());
        }
        if (beerDTO.getBeerStyle() != null) {
            existing.setBeerStyle(beerDTO.getBeerStyle());
        }
        if (beerDTO.getPrice() != null) {
            existing.setPrice(beerDTO.getPrice());
        }
        if (beerDTO.getQuantityOnHand() != null) {
            existing.setQuantityOnHand(beerDTO.getQuantityOnHand());

        }
        if (StringUtils.hasText(beerDTO.getUpc())) {
            existing.setUpc(beerDTO.getUpc());
        }
        return Optional.of(existing);
    }
}
