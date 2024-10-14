package spring.framework.beerworks.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import spring.framework.beerworks.Exceptions.NotFoundException;
import spring.framework.beerworks.model.BeerDTO;
import spring.framework.beerworks.model.BeerStyle;
import spring.framework.beerworks.services.BeerService;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController

public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";


    private final BeerService beerService;


    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beerDTO) {
        beerService.patchBeerById(beerId, beerDTO);


        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId) {
        if (!beerService.deleteById(beerId)) {
            throw new NotFoundException("Beer id not found");
        }


        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("beerId") UUID beerId, @Validated @RequestBody BeerDTO beerDTO) {


        if (beerService.updateBeerById(beerId, beerDTO).isEmpty()) {
            throw new NotFoundException("Beer not found");
        }


        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PostMapping(BEER_PATH)

    public ResponseEntity handlePost(@Validated @RequestBody BeerDTO beerDTO) {

        BeerDTO savedBeerDTO = beerService.saveNewBeer(beerDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_PATH + "/" + savedBeerDTO.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }


    @GetMapping(value = BEER_PATH)
    public Page<BeerDTO> beerList(@RequestParam(required = false) String beerName,
                                  @RequestParam(required = false)BeerStyle beerStyle,
                                  @RequestParam(required = false) Boolean showInventory,
                                  @RequestParam(required = false) Integer pageNumber,
                                  @RequestParam(required = false) Integer pageSize) {

        return beerService.beerList(beerName, beerStyle, showInventory, pageNumber, pageSize);
    }


    @GetMapping(value = BEER_PATH_ID)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId) {

        log.debug("Get BeerDTO by Id in controller");


        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);

    }


}
