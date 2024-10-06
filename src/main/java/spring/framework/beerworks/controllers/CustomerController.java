package spring.framework.beerworks.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.framework.beerworks.Exceptions.NotFoundException;
import spring.framework.beerworks.model.CustomerDTO;
import spring.framework.beerworks.services.CustomerService;

import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@RestController

public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";


    private final CustomerService customerService;

    @PatchMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomerPatchById(@PathVariable("customerId") UUID customerId,@RequestBody CustomerDTO customerDTO) {

        customerService.patchCustomerById(customerId, customerDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }




    @DeleteMapping(CUSTOMER_PATH_ID)
    public ResponseEntity deleteCustomerById(@PathVariable("customerId") UUID customerId){

        customerService.deleteById(customerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    @PutMapping(CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomerById(@PathVariable("customerId")UUID customerId, @RequestBody CustomerDTO customerDTO){

        customerService.updateCustomerById(customerId, customerDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PostMapping(CUSTOMER_PATH)

    public ResponseEntity handlePost(@RequestBody CustomerDTO customerDTO){

        CustomerDTO savedNewCustomerDTO = customerService.saveNewCustomer(customerDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",CUSTOMER_PATH+ "/" + savedNewCustomerDTO.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }


    @GetMapping(CUSTOMER_PATH)
    public List<CustomerDTO> customerList() {
        return customerService.getCustomerList();
    }


    @GetMapping(value = CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID customerId) {



        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);

    }




}
