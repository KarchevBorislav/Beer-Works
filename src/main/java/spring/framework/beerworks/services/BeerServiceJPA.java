package spring.framework.beerworks.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import spring.framework.beerworks.entities.Beer;
import spring.framework.beerworks.mappers.BeerMapper;
import spring.framework.beerworks.model.BeerDTO;
import spring.framework.beerworks.repositories.BeerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> beerList() {

        return beerRepository.findAll().stream().map(beerMapper::beerToBeerDto).collect(Collectors.toList());
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
    public void deleteById(UUID beerId) {
        beerRepository.deleteById(beerId);

    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beerDTO) {
        Optional<Beer> byId = beerRepository.findById(beerId);

        beerRepository.findById(byId.get().getId()).map(beerMapper::beerToBeerDto);
    }
}
