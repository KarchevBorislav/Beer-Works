package spring.framework.beerworks.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import spring.framework.beerworks.entities.Beer;
import spring.framework.beerworks.mappers.BeerMapper;
import spring.framework.beerworks.model.BeerDTO;
import spring.framework.beerworks.model.BeerStyle;
import spring.framework.beerworks.repositories.BeerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> beerList(String beerName, BeerStyle beerStyle, Boolean showInventory) {

        List<Beer> beerList;

        if (StringUtils.hasText(beerName) && beerStyle == null) {
            beerList = listBeerByName(beerName);
        } else if (!StringUtils.hasText(beerName) && beerStyle != null) {
            beerList = listBeerByStyle(beerStyle);
        } else if (StringUtils.hasText(beerName) && beerStyle != null){
            beerList = listBeerByNameAndStyle(beerName,beerStyle);

        }else {
            beerList = beerRepository.findAll();
        }

        if (showInventory!=null && !showInventory) {
            beerList.forEach(beer -> {beer.setQuantityOnHand(null);});
        }

        return beerList.stream()
                .map(beerMapper::beerToBeerDto).toList();
    }



    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {

        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id).orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {


        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDTO)));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beerDTO) {

        AtomicReference<Optional<BeerDTO>> optionalBeerDTO = new AtomicReference<>();
        beerRepository.findById(beerId).ifPresentOrElse(foundedBeer ->
        {
            foundedBeer.setBeerName(beerDTO.getBeerName());
            foundedBeer.setBeerStyle(beerDTO.getBeerStyle());
            foundedBeer.setUpc(beerDTO.getUpc());
            foundedBeer.setPrice(beerDTO.getPrice());
            beerRepository.save(foundedBeer);
            optionalBeerDTO.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(foundedBeer))));
        }, () -> {
            optionalBeerDTO.set(Optional.empty());
        });

        return optionalBeerDTO.get();

    }


    @Override
    public Boolean deleteById(UUID beerId) {

        if (beerRepository.existsById(beerId)) {
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;


    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beerDTO) {
        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer -> {
            if (StringUtils.hasText(beerDTO.getBeerName())) {
                foundBeer.setBeerName(beerDTO.getBeerName());
            }
            if (beerDTO.getBeerStyle() != null) {
                foundBeer.setBeerStyle(beerDTO.getBeerStyle());
            }
            if (StringUtils.hasText(beerDTO.getUpc())) {
                foundBeer.setUpc(beerDTO.getUpc());
            }
            if (beerDTO.getPrice() != null) {
                foundBeer.setPrice(beerDTO.getPrice());
            }
            if (beerDTO.getQuantityOnHand() != null) {
                foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
            }
            atomicReference.set(Optional.of(beerMapper
                    .beerToBeerDto(beerRepository.save(foundBeer))));
        }, () -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }


    private List<Beer> listBeerByNameAndStyle(String beerName,BeerStyle beerStyle) {


        return beerRepository.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle("%" + beerName + "%",beerStyle);
    }

    private List<Beer> listBeerByStyle(BeerStyle beerStyle) {

        return beerRepository.findAllByBeerStyle(beerStyle);
    }

    private List<Beer> listBeerByName(String beerName) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" + beerName + "%");
    }



}
