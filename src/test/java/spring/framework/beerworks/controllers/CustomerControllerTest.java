package spring.framework.beerworks.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import spring.framework.beerworks.model.Beer;
import spring.framework.beerworks.model.Customer;
import spring.framework.beerworks.services.CustomerService;
import spring.framework.beerworks.services.CustomerServiceImpl;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CustomerController.class)
class CustomerControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;


    CustomerServiceImpl customerServiceImpl ;
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

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

    @Test
    void testCreateCustomer() throws Exception {
        Customer customer = customerServiceImpl.getCustomerList().get(0);

        customer.setCustomerName(null);
        customer.setId(null);

        given(customerService.saveNewCustomer(any(Customer.class))).willReturn(customerServiceImpl.getCustomerList().get(1));


        mockMvc.perform(post("/api/v1/customer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content (jacksonObjectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));


    }

}

