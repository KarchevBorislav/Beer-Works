package spring.framework.beerworks.services;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import spring.framework.beerworks.model.CustomerDTO;

import java.time.LocalDateTime;
import java.util.*;

@Service

public class CustomerServiceImpl implements CustomerService {


    private Map<UUID, CustomerDTO> customerMap;


    public CustomerServiceImpl() {
        customerMap = new HashMap<>();


        CustomerDTO customerDTO1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName("John Doe")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        CustomerDTO customerDTO2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .customerName("John Snow")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerMap.put(customerDTO1.getId(), customerDTO1);
        customerMap.put(customerDTO2.getId(), customerDTO2);
    }


    @Override
    public Optional<CustomerDTO> getCustomerById(UUID customerId) {


        return Optional.of(customerMap.get(customerId));
    }


    @Override
    public List<CustomerDTO> getCustomerList(){
        return new ArrayList<CustomerDTO>(customerMap.values());
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) {
        CustomerDTO newCustomerDTO = CustomerDTO.builder()
                .customerName(customerDTO.getCustomerName())
                .id(UUID.randomUUID())
                .version(customerDTO.getVersion())
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();


        customerMap.put(newCustomerDTO.getId(), newCustomerDTO);

        return newCustomerDTO;
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customerDTO) {

        CustomerDTO existing = customerMap.get(customerId);
        existing.setCustomerName(customerDTO.getCustomerName());

        existing.setVersion(customerDTO.getVersion());


        customerMap.put(existing.getId(), existing);

        return Optional.of(existing);
    }

    @Override
    public Boolean deleteById(UUID customerId) {

        if (customerMap.containsKey(customerId)){
            customerMap.remove(customerId);
            return true;
        }
        return false;

    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customerDTO) {
        CustomerDTO existing = customerMap.get(customerId);

        if (StringUtils.hasText(existing.getCustomerName())) {
            existing.setCustomerName(customerDTO.getCustomerName());
        }
        if (existing.getId() != null){

            existing.setId(customerDTO.getId());
        }
        if (existing.getVersion() != null){
            existing.setVersion(customerDTO.getVersion());
        }
        return Optional.of(existing);

    }
}
