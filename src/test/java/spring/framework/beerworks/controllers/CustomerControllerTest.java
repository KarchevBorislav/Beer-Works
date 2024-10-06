package spring.framework.beerworks.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import spring.framework.beerworks.Exceptions.NotFoundException;
import spring.framework.beerworks.model.Beer;
import spring.framework.beerworks.model.Customer;
import spring.framework.beerworks.services.CustomerService;
import spring.framework.beerworks.services.CustomerServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CustomerController.class)
class CustomerControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    @Captor
    ArgumentCaptor<UUID> captor ;

    @Captor
    ArgumentCaptor<Customer> customerCaptor ;

    CustomerServiceImpl customerServiceImpl;
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

        given(customerService.getCustomerById(customerTest.getId())).willReturn(Optional.of(customerTest));


        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID,customerTest.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.customerName", is(customerTest.getCustomerName())));

    }

    @Test
    void testCreateCustomer() throws Exception {
        Customer customer = customerServiceImpl.getCustomerList().get(0);

        customer.setCustomerName(null);
        customer.setId(null);

        given(customerService.saveNewCustomer(any(Customer.class))).willReturn(customerServiceImpl.getCustomerList().get(1));


        mockMvc.perform(post(CustomerController.CUSTOMER_PATH )
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jacksonObjectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));


    }

    @Test
    void testUpdateCustomer() throws Exception {
        Customer testCustomer = customerServiceImpl.getCustomerList().get(0);

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID , testCustomer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isNoContent());


    }


    @Test
    void testDeleteCustomer() throws Exception {

        Customer customer = customerServiceImpl.getCustomerList().get(0);

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID , customer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());


        verify(customerService).deleteById(captor.capture());

        assertThat(customer.getId()).isEqualTo(captor.getValue());


    }

    @Test
    void testPatchController() throws Exception {

        Customer customer = customerServiceImpl.getCustomerList().get(0);

        Map<String,Object> customerMap = new HashMap<>();
        customerMap.put("Customer", "New Customer");


        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID ,customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        verify(customerService).patchCustomerById(captor.capture(), customerCaptor.capture());

        assertThat(customer.getId()).isEqualTo(captor.getValue());
        assertThat(customerMap.get("customerName")).isEqualTo(customerCaptor.getValue().getCustomerName());

    }


    @Test
    void getCustomerByIdNotFound() throws Exception {
        given(customerService.getCustomerById(any(UUID.class))).willReturn(Optional.empty());
        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}


