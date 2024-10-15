package spring.framework.beerworks.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import spring.framework.beerworks.entities.Beer;
import spring.framework.beerworks.entities.Category;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository repository;


    @Autowired
    BeerRepository beerRepository;
    Beer testBeer;
    @Autowired
    private CategoryRepository categoryRepository;


    @BeforeEach
    void setUp() {
        testBeer = beerRepository.findAll().get(0);
    }

    @Transactional
    @Test
    void testAddCategory() {
        Category stout = categoryRepository.save(Category.builder().description("Stout").build());

        testBeer.addCategory(stout);
        Beer save = beerRepository.save(testBeer);


    }
}