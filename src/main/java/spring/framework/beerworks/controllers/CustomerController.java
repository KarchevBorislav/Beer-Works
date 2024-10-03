package spring.framework.beerworks.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.framework.beerworks.model.Beer;
import spring.framework.beerworks.model.Customer;
import spring.framework.beerworks.services.CustomerService;

import java.util.List;
import java.util.UUID;



@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@RestController

public class CustomerController {

    private final CustomerService customerService;

    @PatchMapping("{customerId}")
    public ResponseEntity updateCustomerPatchById(@PathVariable("customerId") UUID customerId,@RequestBody Customer customer) {

        customerService.patchCustomerById(customerId,customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }




    @DeleteMapping("{customerId}")
    public ResponseEntity deleteCustomerById(@PathVariable("customerId") UUID customerId){

        customerService.deleteById(customerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    @PutMapping("{customerId}")
    public ResponseEntity updateCustomerById(@PathVariable("customerId")UUID customerId, @RequestBody Customer customer ){

        customerService.updateCustomerById(customerId,customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PostMapping

    public ResponseEntity handlePost(@RequestBody Customer customer){

        Customer savedNewCustomer = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/customer/" + savedNewCustomer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> customerList() {
        return customerService.getCustomerList();
    }


    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {



        return customerService.getCustomerById(customerId);

    }




}
