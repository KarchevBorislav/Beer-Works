package spring.framework.beerworks.mappers;

import org.mapstruct.Mapper;

import spring.framework.beerworks.entities.Customer;

import spring.framework.beerworks.model.CustomerDTO;

@Mapper
public interface CustomerMapper {


    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToDto(Customer customer);
}
