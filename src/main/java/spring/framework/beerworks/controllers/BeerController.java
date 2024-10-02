package spring.framework.beerworks.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import spring.framework.beerworks.model.Beer;
import spring.framework.beerworks.services.BeerService;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {

    private final BeerService beerService;


    @RequestMapping("/api/v1/beer")
    public List<Beer> beerList(){
        return beerService.beerList();
    }

    public Beer getBeerById(UUID id) {

       log.debug("Get Beer by Id in controller");


        return beerService.getBeerById(id);

    }


}
