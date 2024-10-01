package spring.framework.beerworks.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import spring.framework.beerworks.model.Beer;
import spring.framework.beerworks.services.BeerService;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {


    private final BeerService beerService;

    public Beer getBeerById(UUID id) {

       log.debug("Get Beer by Id in controller");


        return beerService.getBeerById(id);

    }


}
