package spring.framework.beerworks.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.framework.beerworks.model.Customer;
import spring.framework.beerworks.services.CustomerService;

import java.util.List;
import java.util.UUID;



@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@RestController

public class CustomerController {

    private final CustomerService customerService;


    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> customerList() {
        return customerService.getCustomerList();
    }


    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {



        return customerService.getCustomerById(customerId);

    }


}
