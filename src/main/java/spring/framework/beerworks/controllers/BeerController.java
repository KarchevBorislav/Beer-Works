package spring.framework.beerworks.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spring.framework.beerworks.model.Beer;
import spring.framework.beerworks.services.BeerService;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;


    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> beerList() {
        return beerService.beerList();
    }



    @RequestMapping(value = "/{beerId}",method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId) {

        log.debug("Get Beer by Id in controller");


        return beerService.getBeerById(beerId);

    }


}
