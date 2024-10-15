package spring.framework.beerworks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.framework.beerworks.entities.Category;

import java.util.UUID;

public interface CategoryRepository  extends JpaRepository<Category, UUID> {
}
