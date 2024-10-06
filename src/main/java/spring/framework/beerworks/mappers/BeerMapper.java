package spring.framework.beerworks.mappers;


import org.mapstruct.Mapper;
import spring.framework.beerworks.entities.Beer;
import spring.framework.beerworks.model.BeerDTO;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer );
}
