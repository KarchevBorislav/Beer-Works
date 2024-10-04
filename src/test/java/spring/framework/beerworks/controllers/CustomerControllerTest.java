package spring.framework.beerworks.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import spring.framework.beerworks.model.Customer;
import spring.framework.beerworks.services.CustomerService;
import spring.framework.beerworks.services.CustomerServiceImpl;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CustomerController.class)
class CustomerControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CustomerService customerService;


    CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();


    @Test
    void testGetCustomerList() throws Exception {

        given(customerService.getCustomerList()).willReturn(customerServiceImpl.getCustomerList());

        mockMvc.perform(get("/api/v1/customer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)));



    }


    @Test
    void getCustomerById() throws Exception {
        Customer customerTest = customerServiceImpl.getCustomerList().get(0);

        given(customerService.getCustomerById(customerTest.getId())).willReturn(customerTest);


        mockMvc.perform(get("/api/v1/customer/" + customerTest.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON ))
                .andExpect(jsonPath("$.id",is(customerTest.getId().toString())))
                .andExpect(jsonPath("$.customerName",is(customerTest.getCustomerName())));

    }

}

