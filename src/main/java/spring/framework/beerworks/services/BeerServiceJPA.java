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

        return null;
    }

    @Override
    public void updateBeerById(UUID beerId, BeerDTO beerDTO) {

    }

    @Override
    public void deleteById(UUID beerId) {

    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beerDTO) {

    }
}
