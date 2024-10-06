package spring.framework.beerworks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.framework.beerworks.entities.Customer;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
